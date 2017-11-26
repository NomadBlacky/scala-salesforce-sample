package org.nomadblacky.salesforce.sample.oauth

import spray.json.{DefaultJsonProtocol, DeserializationException, JsObject, JsString, JsValue, RootJsonFormat, _}

import scalaj.http.{Http, HttpResponse}

case class OAuthResult(
  accessToken: String,
  instanceUrl: String,
  id: String,
  tokenType: String,
  issuedAt: String,
  signature: String
)

object OAuthResultJsonProtocol extends DefaultJsonProtocol {
  implicit object OAuthResultJsonFormat extends RootJsonFormat[OAuthResult] {
    override def write(r: OAuthResult): JsValue =
      JsObject(
        ("accessToken" , JsString(r.accessToken)),
        ("instance_url", JsString(r.instanceUrl)),
        ("id"          , JsString(r.id)),
        ("token_type"  , JsString(r.tokenType)),
        ("issued_at"   , JsString(r.issuedAt)),
        ("signature"   , JsString(r.signature))
      )

    override def read(json: JsValue): OAuthResult = {
      json.asJsObject.getFields("access_token", "instance_url", "id", "token_type", "issued_at", "signature") match {
        case Seq(JsString(accessToken), JsString(instanceUrl), JsString(id), JsString(tokenType), JsString(issuedAt), JsString(signature)) =>
          OAuthResult(accessToken, instanceUrl, id, tokenType, issuedAt, signature)
        case _ =>
          throw DeserializationException(s"Parse Failed: $json")
      }
    }
  }
}

object OAuthClient {
  import OAuthResultJsonProtocol._

  def auth(clientId: String, clientSecret: String, username: String, passwordAndToken: String): OAuthResult = {
    val params = Seq(
      ("grant_type", "password"),
      ("client_id", clientId),
      ("client_secret", clientSecret),
      ("username", username),
      ("password", passwordAndToken)
    )
    val response = Http("https://login.salesforce.com/services/oauth2/token").postForm(params).asString
    response match {
      case HttpResponse(body, 200, _) => body.parseJson.convertTo[OAuthResult]
      case HttpResponse(body, _, _) => throw new IllegalStateException(s"Authentication Failed: $body")
    }
  }
}