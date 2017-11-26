package example

import org.scalatest._
import spray.json._

class OAuthTest extends FunSuite with Matchers {
  test("Read OAuth result") {
    import OAuthResultJsonProtocol._
    val source =
      """{
        |	"access_token": "ACCESS_TOKEN",
        |	"instance_url": "https://ap5.salesforce.com",
        |	"id": "https://login.salesforce.com/id/HOGE/FOO",
        |	"token_type": "Bearer",
        |	"issued_at": "ISSUED_AT",
        |	"signature": "SIGNATURE"
        |}
      """.stripMargin
    val json = source.parseJson
    val result = json.convertTo[OAuthResult]

    result shouldBe OAuthResult(
      accessToken = "ACCESS_TOKEN",
      instanceUrl = "https://ap5.salesforce.com",
      id          = "https://login.salesforce.com/id/HOGE/FOO",
      tokenType   = "Bearer",
      issuedAt    = "ISSUED_AT",
      signature   = "SIGNATURE"
    )
  }
}
