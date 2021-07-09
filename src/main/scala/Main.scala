package icfpc21.classified

import icfpc21.classified.api.PosesClient

object Main extends App {
  Console.println("Hello, icfpc")

  val client = new PosesClient.Live("https://poses.live", "cedad5a6-da68-4ff4-984a-880e5214866b")

  val problem = client.getProblem(1)
}
