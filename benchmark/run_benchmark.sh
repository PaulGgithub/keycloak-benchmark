#!/bin/bash

./keycloak-benchmark-0.7-SNAPSHOT/bin/kcb.sh \
--scenario=keycloak.scenario.authentication.BrowserFlowWithIdentityCookeAndRefreshAndLogout \
--measurement=1500 \
--users-per-realm=10000 \
--clients-per-realm=5 \
--refresh-token-count=6 \
--refresh-token-period=60 \
--users-per-sec=2000 \
--ramp-up=180 \
--warm-up=1500