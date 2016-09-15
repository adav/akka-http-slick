package com.knoldus.connection

import org.slf4j.LoggerFactory

trait PostgresDBImpl extends DBComponent {

  val driver = slick.driver.PostgresDriver
  val db = PostgresDB.connectionPool

}

private[connection] object PostgresDB {
  val logger = LoggerFactory.getLogger(this.getClass)

  val dbURL = "jdbc:" + sys.env.getOrElse("DATABASE_URL", "postgres://localhost:5432/?user=adav&password=password").replace("postgres://", "postgresql://")

  import slick.driver.PostgresDriver.api._

  logger.info(s"Setting up db at DATABASE_URL=$dbURL .......")

  val connectionPool = Database.forURL(url = dbURL, driver = "org.postgresql.Driver" )

  connectionPool.source.createConnection().close()

}

// local dev run:
// docker run --name feast-postgres -p 5432:5432 -e POSTGRES_PASSWORD=password -e POSTGRES_USER=adav -d postgres