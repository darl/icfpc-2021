package icfpc21.classified
package model

import java.time.Instant

case class Submission(id: String, date: Instant, ok: Boolean, dislikes: Option[Int])
