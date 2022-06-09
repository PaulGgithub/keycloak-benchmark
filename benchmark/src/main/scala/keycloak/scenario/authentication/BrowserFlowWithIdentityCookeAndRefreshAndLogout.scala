package keycloak.scenario.authentication

import keycloak.scenario.{CommonSimulation, KeycloakScenarioBuilder}

class BrowserFlowWithIdentityCookeAndRefreshAndLogout extends CommonSimulation {

  setUp("Authentication - Authorization Code Username/Password", new KeycloakScenarioBuilder()
    .openLoginPage(true)
    .loginUsernamePassword()
    .exchangeCode()
    .refreshTokenMultipleTimes()
    .loginWithIdenitityCookie()
    .exchangeCodeFromCookieLogin()
    .logoutSession())

}