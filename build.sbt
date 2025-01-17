import org.scalajs.linker.interface.ModuleSplitStyle

ThisBuild / scalaVersion :=  DependencyVersions.scalaVersion

ThisBuild / publishMavenStyle := true

ThisBuild / publishTo := {
  val releasesRepo = s"https://maven.pkg.github.com/yash-a-18/dataimportcsv3s"
  Some("github" at releasesRepo)
}

credentials += Credentials(
  "GitHub Package Registry",
  "maven.pkg.github.com",
  "yash-a-18", // Replace with your GitHub username
  sys.env.getOrElse("GH_TOKEN", "") // Fetch token from environment variable
)

// give the user a nice default project!
val sharedSettings = Seq(
  organization := "com.axiom",
  scalacOptions ++=  Seq("-Yretain-trees",//necessary in zio-json if any case classes have default parameters
  "-Xmax-inlines","50") //setting max inlines to accomodate > 32 fields in case classes
)


lazy val root = project.in(file(".")).
  aggregate(dataimportcsv3s.js, dataimportcsv3s.jvm).
  settings(sharedSettings,
    publish := {},
    publishLocal := {}

  )

lazy val dataimportcsv3s = crossProject(JSPlatform, JVMPlatform).crossType(CrossType.Full).in(file("."))
  .settings(
    name := "dataimportcsv3s",
    version := "0.0.1-SNAPSHOT",
    sharedSettings,
    libraryDependencies ++= Dependencies.zioJson.value,
    libraryDependencies ++= Dependencies.scalatest.value,
    libraryDependencies += "io.github.cquiroz" %%% "scala-java-time" % "2.5.0"

  ).
  jvmSettings(
    // Add JVM-specific settings here
    //this maximizes the number of inlines for the csv3s macros for decoding case classes > 22 fields
    // libraryDependencies ++= Seq(
    //   Dependencies.zioHttp, 
    //   Dependencies.zioTest, 
    //   Dependencies.zioTestSBT, 
    //   Dependencies.zioTestMagnolia,
    //   Dependencies.zioTypesafeConfig,
    //   Dependencies.zioLogging
      
    // ),
    libraryDependencies ++= Dependencies.scalaLogging.value,
    libraryDependencies += Dependencies.betterfiles,
    libraryDependencies += Dependencies.csv3s,
    libraryDependencies += "com.typesafe" % "config" % "1.4.2"

      


  ).
  jsSettings(
    

    /* Configure Scala.js to emit modules in the optimal way to
     * connect to Vite's incremental reload.
     * - emit ECMAScript modules
     * - emit as many small modules as possible for classes in the "livechart" package
     * - emit as few (large) modules as possible for all other classes
     *   (in particular, for the standard library)
     */


    scalaJSLinkerConfig ~= {
      _.withModuleKind(ModuleKind.ESModule)
        .withModuleSplitStyle(
          ModuleSplitStyle.SmallModulesFor(List("dataimportcvs3s")))
    },

    /*
     *add resolver for scalatest
     */
    resolvers += "Artima Maven Repository" at "https://repo.artima.com/releases",


    // Add JS-specific settings here
    //TODO move and update this dependency to include laminarext so that I can play with the Fetch functionality
    libraryDependencies ++= Dependencies.laminar.value,
    scalaJSUseMainModuleInitializer := true,
  )