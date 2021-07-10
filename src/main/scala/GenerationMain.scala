package icfpc21.classified

import api.PosesClient
import optimizer.{GenerationalSolver, Scorer}
import visualization.Visualizer
import model._

object GenerationMain extends App {
  val problemId = 88 // args(1).toInt
  val client = new PosesClient.Live("https://poses.live", Key.value)

  val checkpoints = Map(
    // dislikes: 247.0
    4 -> Solution(IndexedSeq(Vector(12,11), Vector(5,27), Vector(5,37), Vector(5,6), Vector(9,33), Vector(9,53), Vector(10,23), Vector(26,32), Vector(26,37), Vector(24,52), Vector(24,57), Vector(30,69), Vector(32,51), Vector(31,62), Vector(48,22), Vector(34,17), Vector(31,29), Vector(36,47), Vector(36,63), Vector(31,90), Vector(41,52), Vector(41,57), Vector(47,75), Vector(51,22), Vector(51,52), Vector(51,57), Vector(65,15), Vector(55,32), Vector(56,37), Vector(56,47), Vector(56,66), Vector(60,92), Vector(61,47), Vector(61,63), Vector(94,50), Vector(66,52), Vector(68,57), Vector(70,70), Vector(77,32), Vector(78,37), Vector(78,54), Vector(95,5), Vector(88,36))),
    34 -> Solution(IndexedSeq(Vector(49,67), Vector(51,39), Vector(26,56), Vector(59,12), Vector(50,40), Vector(25,27), Vector(2,43), Vector(49,12), Vector(24,25))),
    9 -> Solution(IndexedSeq(Vector(134,6), Vector(56,8), Vector(120,8), Vector(52,5), Vector(122,17), Vector(66,13), Vector(114,18), Vector(66,17), Vector(51,16), Vector(104,12), Vector(52,19), Vector(53,23), Vector(49,24), Vector(90,36), Vector(66,44), Vector(60,47), Vector(16,30), Vector(10,24), Vector(146,80), Vector(18,39), Vector(68,63), Vector(49,64), Vector(102,75), Vector(155,80), Vector(136,80), Vector(120,78), Vector(7,42), Vector(93,76), Vector(11,52), Vector(145,83), Vector(80,78), Vector(72,77), Vector(89,85), Vector(98,86), Vector(132,92), Vector(134,94), Vector(78,84), Vector(71,82), Vector(90,92), Vector(129,95), Vector(120,97), Vector(77,90), Vector(68,88), Vector(125,97), Vector(118,101), Vector(85,93), Vector(125,100), Vector(86,97), Vector(80,96), Vector(75,96), Vector(68,95), Vector(83,100), Vector(14,140), Vector(17,135), Vector(75,103), Vector(67,102), Vector(83,107), Vector(13,146), Vector(130,137), Vector(89,111), Vector(78,114), Vector(74,109), Vector(68,109), Vector(104,117), Vector(41,114), Vector(122,130), Vector(85,116), Vector(7,146), Vector(71,115), Vector(67,116), Vector(6,155), Vector(139,140), Vector(83,125), Vector(132,132), Vector(56,124), Vector(20,94), Vector(68,129), Vector(56,137), Vector(72,149), Vector(38,106), Vector(28,99), Vector(59,148), Vector(70,153), Vector(48,109), Vector(55,155), Vector(49,154), Vector(44,109), Vector(56,150), Vector(51,149), Vector(47,147), Vector(39,115), Vector(46,142), Vector(48,142), Vector(48,146), Vector(31,97), Vector(34,102))),
  )

  val problem = client.getProblem(problemId)
  println("Eps = " + problem.epsilon)
  val problem2 = checkpoints.get(problemId) match {
    case Some(checkpoint) => problem.copy(figure = problem.figure.copy(vertices = checkpoint.vertices))
    case None             => problem
  }
  val visualizer = Visualizer(problem2)
  val t = new Thread(() => visualizer.show())
  t.start()
  val solution = new GenerationalSolver(visualizer).solve(problem2)
  t.join()

  println(solution)
  val score = Scorer.score(problem2.figure.copy(vertices = solution.vertices), problem2)
  if (score.valid && score.fits) {
    println("Sending solution")
    client.submitSolution(problemId, solution)
  } else {
    println("Ignored solution")
  }

}
