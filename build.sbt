import com.typesafe.sbt.SbtScalariform
import com.typesafe.sbt.SbtScalariform.ScalariformKeys

import scalariform.formatter.preferences._

name := "akka-http-extensions"

organization := "com.lonelyplanet"

version := "0.4.6"

scalaVersion := "2.11.8"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-feature",
  "-language:postfixOps",
  "-language:implicitConversions",
  "-unchecked",
  "-deprecation"
)

resolvers ++= Seq(
  "OSS" at "http://oss.sonatype.org/content/repositories/releases/",
  Resolver.bintrayRepo("lonelyplanet", "maven")
)

libraryDependencies ++= {
  val akkaVersion           = "2.4.8"
  val scalaTestVersion      = "3.0.0"
  val logbackClassicVersion = "1.1.6"

  Seq(
    "ch.qos.logback"       %  "logback-classic"                      % logbackClassicVersion % "provided",
    "com.typesafe.akka"    %% "akka-actor"                           % akkaVersion % "provided",
    "com.typesafe.akka"    %% "akka-stream"                          % akkaVersion % "provided",
    "com.typesafe.akka"    %% "akka-http-core"                       % akkaVersion % "provided",
    "com.typesafe.akka"    %% "akka-http-experimental"               % akkaVersion % "provided",
    "com.typesafe.akka"    %% "akka-http-spray-json-experimental"    % akkaVersion % "provided",
    "com.typesafe.akka"    %% "akka-http-testkit"                    % akkaVersion % "test",
    "org.scalatest"        %% "scalatest"                            % scalaTestVersion % "test",
    ("com.lonelyplanet"    %% "scala-util"                           % "0.1.8").exclude("ch.qos.logback", "logback-classic"),
    "org.zalando"          %% "scala-jsonapi"                        % "0.5.2" % "provided"
  )
}


SbtScalariform.scalariformSettings

ScalariformKeys.preferences := ScalariformKeys.preferences.value
  .setPreference(AlignSingleLineCaseStatements, true)
  .setPreference(DoubleIndentClassDeclaration, true)
  .setPreference(SpacesAroundMultiImports, false)
  .setPreference(CompactControlReadability, false)

bintrayOrganization := Some("lonelyplanet")

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.txt"))

val doNotPublishSettings = Seq(publish := {})

val publishSettings =
  if (version.toString.endsWith("-SNAPSHOT"))
    Seq(
      publishTo := Some("Artifactory Realm" at "http://oss.jfrog.org/artifactory/oss-snapshot-local"),
      bintrayReleaseOnPublish := false,
      credentials := List(Path.userHome / ".bintray" / ".artifactory").filter(_.exists).map(Credentials(_))
    )
  else
    Seq(
      organization := "com.lonelyplanet",
      pomExtra := <scm>
        <url>https://github.com/lonelyplanet/akka-http-extensions</url>
        <connection>https://github.com/lonelyplanet/akka-http-extensions</connection>
      </scm>
        <developers>
          <developer>
            <id>wlk</id>
            <name>Wojciech Langiewicz</name>
            <url>https://github.com/lonelyplanet/akka-http-extensions</url>
          </developer>
        </developers>,
      publishArtifact in Test := false,
      homepage := Some(url("https://github.com/lonelyplanet/akka-http-extensions")),
      publishMavenStyle := false,
      resolvers += Resolver.url("lonelyplanet ivy resolver", url("http://dl.bintray.com/lonelyplanet/maven"))(Resolver.ivyStylePatterns)
    )