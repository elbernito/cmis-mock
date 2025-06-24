### 3. Vergleich Implementierung vs. Spezifikation – Ausstehende Änderungen

| Service-Kategorie         | Operation                       | Vorhanden? | Kommentar / Änderungspunkte                                 |
|---------------------------|---------------------------------|:----------:|-------------------------------------------------------------|
| **Repository Service**    | getRepositories                 | ✔️         |                                                             |
|                           | getRepositoryInfo               | ✔️         |                                                             |
| **Navigation Service**    | getChildren                     | ❌         | Implementierung fehlt; REST-Endpunkt erforderlich           |
|                           | getDescendants                  | ❌         |                                                             |
|                           | getFolderParent                 | ❌         |                                                             |
|                           | getCheckedOutDocs               | ❌         |                                                             |
| **Object Service**        | getObject                       | ✔️         |                                                             |
|                           | getObjectByPath                 | ✔️         |                                                             |
|                           | moveObject                      | ✔️         |                                                             |
|                           | copyObject                      | ✔️         |                                                             |
|                           | deleteObject                    | ✔️         |                                                             |
| **Multi-filing Service**  | addObjectToFolder               | ❌         | Fehlt in `CmisV12MockController`                           |
|                           | removeObjectFromFolder          | ❌         | Fehlt                                                      |
| **Versioning Service**    | checkOut                        | ✔️         | Methode heißt `checkoutDocument` – Namensangleichung prüfen |
|                           | checkIn                         | ✔️         | Methode heißt `checkinDocument`                            |
|                           | getAllVersions                  | ✔️         | Implementiert als `getDocumentVersions`                    |
|                           | cancelCheckOut                  | ❌         | Fehlt                                                      |
| **Relationship Service**   | createRelationship              | ✔️         |                                                             |
|                           | getObjectRelationships          | ✔️         |                                                             |
|                           | getRelsBySourceId               | ✔️         | Implementiert als `getRelationshipsByObjectId`             |
|                           | getRelsByTargetId               | ❌         | Fehlt                                                      |
|                           | deleteRelationship              | ✔️         |                                                             |
| **Policy Service**        | applyPolicy                     | ✔️         | Implementiert als `applyPolicyToObject`                    |
|                           | removePolicy                    | ✔️         | Implementiert als `removePolicyFromObject`                 |
|                           | getAppliedPolicies              | ❌         | Fehlt                                                      |
| **ACL Service**           | getAcl                          | ❌         | Fehlt                                                      |
|                           | applyAcl                        | ✔️         | Implementiert als `setAclForObject`                        |
|                           | getPermissions                  | ❌         | Fehlt                                                      |
| **Discovery Service**     | query                           | ❌         | Fehlt                                                      |
|                           | getContentChanges               | ✔️         |                                                             |
| **Retention Service**     | createRetention                 | ✔️         |                                                             |
|                           | getRetention                    | ✔️         |                                                             |
|                           | getRetentionsByObjectId         | ✔️         |                                                             |
|                           | deleteRetention                 | ✔️         |                                                             |
| **Type Definition Service** | getTypeDefinition             | ✔️         | Implementiert als `getType`                                |
|                           | getTypeChildren                 | ❌         | `getAllTypeDefinitions` liefert nur flache Liste           |
|                           | getTypeDescendants              | ❌         | Fehlt                                                      |
| **Renditions Service**    | getRenditions                   | ❌         | Fehlt                                                      |
