# Frix region example

MIT portfolio source, not a client reference. 5 local tests cover MockBukkit loading/permissions and region boundaries, dimension scope and invalid coordinates.

Built against Paper API 1.21.11 with JDK 21 and Gradle 9.8.2. Real local Paper 1.21.11 and 26.3 server smoke checks are still pending; do not infer tested runtime compatibility or a live server from unit tests. No GIF/gameplay screenshot is claimed.

```sh
gradle --no-daemon test jar
```

One configured X/Z region in one world. Private entry notice with a five-minute cooldown; no broadcast or external network. Operator-only `/frixregion reload`; invalid bounds disable notices.

No external credentials, webhook, player database or paid service. Tests use synthetic local players. Before client delivery: reproduce the agreed issue, test the agreed server versions, retain a backup and rollback, and obtain Reda’s review of the diff and real test evidence.
