package keycloak.scenario.authentication

import keycloak.scenario.{CommonSimulation, KeycloakScenarioBuilder}

class DirectGrantWithRefreshAndLogout extends CommonSimulation {

  setUp("Direct Grant - Verify Direct Grant flow, refresh token and logout", new KeycloakScenarioBuilder()
    .directGrant()
    .refreshTokenMultipleTimes()
    .logoutSession())
}