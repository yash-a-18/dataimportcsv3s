package com.axiom

import zio._
import zio.http._
import zio.http.endpoint._
// import zio.http.{html => html5, _}


object MainApp extends ZIOAppDefault {
  import zio.config.typesafe.TypesafeConfigProvider
  import zio.logging.fileLogger  

  val configString: String =
      s"""
        |logger {
        |  format = "%timestamp{yyyy-MM-dd'T'HH:mm:ssZ} %fixed{7}{%level} [%fiberId] %name:%line %message %cause"
        |  path = "file:///logs/file_app.log"
        |  rollingPolicy {
        |    type = TimeBasedRollingPolicy
        |  }
        |}
        |""".stripMargin

  val configProvider: ConfigProvider = TypesafeConfigProvider.fromHoconString(configString)

  override val bootstrap: ZLayer[Any, Config.Error, Unit] =
    import zio.logging.consoleLogger    
    Runtime.removeDefaultLoggers >>> Runtime.setConfigProvider(configProvider) >>> fileLogger()
    Runtime.removeDefaultLoggers >>> consoleLogger()  


  

  //
  // Compose your separate http apps to a larger http app 
  //
  import zio.http.codec.PathCodec.trailing
  import zio.http.template._
  

  val directoryRoute =     Method.GET / "static" / trailing -> handler {
      val extractPath    = Handler.param[(Path, Request)](_._1)
      val extractRequest = Handler.param[(Path, Request)](_._2)
      
      val r = for {
        path <- extractPath
        file <- Handler.getResourceAsFile(path.encode)
        http <-
        // Rendering a custom UI to list all the files in the directory
        extractRequest >>> (if (file.isDirectory) {
                              // Accessing the files in the directory
                              val files = file.listFiles.toList.sortBy(_.getName)
                              val base  = "/static/"
                              val rest  = path

                              // Custom UI to list all the files in the directory
                              Handler.template(s"File Explorer ~$base${path}") {
                                ul(
                                  li(a(href := s"$base$rest", "..")),
                                  files.map { file =>
                                    li(
                                      a(
                                        href := s"$base${path.encode}${if (path.isRoot) file.getName
                                          else "/" + file.getName}",
                                        file.getName,
                                      ),
                                    )
                                  },
                                )
                              }
                            }

                            // Return the file if it's a static resource
                            else if (file.isFile) Handler.fromFile(file)

                            // Return a 404 if the file doesn't exist
                            else Handler.notFound)

      } yield http

      r
    }
  val textRoute =
    Method.GET / "text" -> handler(Response.text("Hello World!"))

  val jsonRoute =
    Method.GET / "json" -> handler(Response.json("""{"greetings": "Hello World!"}"""))

  // Create HTTP route
  val routes = Routes(/*directoryRoute*/textRoute, jsonRoute)


  


  //
  // Start your server
  //

  override val run = {
    Console.printLine("please visit http://localhost:8080") *> 
    Server.serve(routes).provide(Server.default)
  }
}
