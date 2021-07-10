package icfpc21.classified
package model

case class Problem(hole: Hole, figure: Figure, bonuses: Seq[BonusPoint], epsilon: Int)
