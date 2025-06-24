#!/bin/bash

PORT=9000
API="http://localhost:$PORT/api/cmis/v1.2"
LOG="cmis_fulltest.log"
DATE="$(date '+%Y-%m-%d %H:%M:%S')"
RAND=$(tr -dc a-z0-9 </dev/urandom | head -c 6)

# Unique Testdaten pro Lauf
FOLDER_ID="folder-$RAND"
DOC_ID="doc-$RAND"
META_ID="meta-$RAND"
META_DOC_ID="meta-doc-$RAND"
META_FOLDER_ID="meta-folder-$RAND"
REL_ID="rel-$RAND"
POL_ID="pol-$RAND"
ACL_ID="acl-$RAND"
RET_ID="ret-$RAND"

echo "==== $DATE: START CMIS 1.2 API FULL TEST (ID $RAND) ====" > "$LOG"
failures=0
successes=0

log_error() { echo "FAIL: $*" | tee -a "$LOG"; ((failures++)); }
log_ok()    { echo "OK: $*" | tee -a "$LOG"; ((successes++)); }
section()   { echo -e "\n==== $1 ====\n" | tee -a "$LOG"; }

check_http() {
  local exp_code="$1"
  local msg="$2"
  local curl_out="$3"
  local code
  code=$(grep -m1 HTTP/ "$curl_out" | awk '{print $2}')
  if [[ "$code" == "$exp_code" ]]; then
    log_ok "$msg (HTTP $code)"
  else
    log_error "$msg (HTTP $code, expected $exp_code)"
  fi
}

TMPRESP="cmis_test_resp.tmp"

#### 1. Repository
section "Repository"
curl -s -i "$API/repositories" > "$TMPRESP"
check_http 200 "GET /repositories" "$TMPRESP"
REPO_ID=$(grep -o '"repositoryId":"[^"]*"' "$TMPRESP" | head -1 | cut -d':' -f2 | tr -d '"')
[ -z "$REPO_ID" ] && REPO_ID="default-repo"

curl -s -i "$API/repositories/$REPO_ID" > "$TMPRESP"
check_http 200 "GET /repositories/$REPO_ID" "$TMPRESP"
curl -s -i "$API/repositories/$REPO_ID/info" > "$TMPRESP"
check_http 200 "GET /repositories/$REPO_ID/info" "$TMPRESP"

#### 2. Folder
section "Folder (Create/CRUD)"
FOLDER_JSON="{\"folderId\":\"$FOLDER_ID\",\"parentFolderId\":null,\"name\":\"TestFolder_$RAND\"}"
curl -s -i -X POST "$API/folders" -H "accept: */*" -H "Content-Type: application/json" -d "$FOLDER_JSON" > "$TMPRESP"
check_http 200 "POST /folders ($FOLDER_ID)" "$TMPRESP"

curl -s -i "$API/folders/$FOLDER_ID" > "$TMPRESP"
check_http 200 "GET /folders/$FOLDER_ID" "$TMPRESP"

FOLDER_UPDATE_JSON="{\"folderId\":\"$FOLDER_ID\",\"parentFolderId\":null,\"name\":\"TestFolderRenamed_$RAND\"}"
curl -s -i -X PUT "$API/folders/$FOLDER_ID" -H "accept: */*" -H "Content-Type: application/json" -d "$FOLDER_UPDATE_JSON" > "$TMPRESP"
check_http 200 "PUT /folders/$FOLDER_ID" "$TMPRESP"

curl -s -i "$API/folders/$FOLDER_ID/children" > "$TMPRESP"
check_http 200 "GET /folders/$FOLDER_ID/children" "$TMPRESP"

curl -s -i "$API/folders/$FOLDER_ID/descendants" > "$TMPRESP"
check_http 200 "GET /folders/$FOLDER_ID/descendants" "$TMPRESP"

curl -s -i "$API/folders/$FOLDER_ID/parent" > "$TMPRESP"
PARENT_CODE=$(grep -m1 HTTP/ "$TMPRESP" | awk '{print $2}')
if [[ "$PARENT_CODE" == "200" || "$PARENT_CODE" == "404" ]]; then
  log_ok "GET /folders/$FOLDER_ID/parent (HTTP $PARENT_CODE, Root-Folder)"
else
  log_error "GET /folders/$FOLDER_ID/parent (HTTP $PARENT_CODE, expected 200/404)"
fi

BADFOLDER_JSON="{\"folderId\":\"bad-$FOLDER_ID\",\"parentFolderId\":null,\"name\":\"\"}"
curl -s -i -X POST "$API/folders" -H "accept: */*" -H "Content-Type: application/json" -d "$BADFOLDER_JSON" > "$TMPRESP"
check_http 400 "POST /folders (missing name, should fail)" "$TMPRESP"

#### 3. Document
section "Document (Create/CRUD/Content)"
DOC_JSON="{\"documentId\":\"$DOC_ID\",\"parentFolderId\":\"$FOLDER_ID\",\"name\":\"TestDoc_$RAND.txt\",\"contentLength\":11,\"mimeType\":\"text/plain\"}"
curl -s -i -X POST "$API/documents" -H "accept: */*" -H "Content-Type: application/json" -d "$DOC_JSON" > "$TMPRESP"
check_http 200 "POST /documents ($DOC_ID)" "$TMPRESP"

curl -s -i "$API/documents/$DOC_ID" > "$TMPRESP"
check_http 200 "GET /documents/$DOC_ID" "$TMPRESP"

DOC_UPDATE_JSON="{\"documentId\":\"$DOC_ID\",\"parentFolderId\":\"$FOLDER_ID\",\"name\":\"TestDocUpdated_$RAND.txt\",\"contentLength\":13,\"mimeType\":\"text/plain\"}"
curl -s -i -X PUT "$API/documents/$DOC_ID" -H "accept: */*" -H "Content-Type: application/json" -d "$DOC_UPDATE_JSON" > "$TMPRESP"
check_http 200 "PUT /documents/$DOC_ID" "$TMPRESP"

curl -s -i -X POST "$API/documents/$DOC_ID/checkin" -H "accept: */*" > "$TMPRESP"
check_http 200 "POST /documents/$DOC_ID/checkin" "$TMPRESP"

curl -s -i -X POST "$API/documents/$DOC_ID/checkout" -H "accept: */*" > "$TMPRESP"
check_http 200 "POST /documents/$DOC_ID/checkout" "$TMPRESP"

curl -s -i "$API/documents/$DOC_ID/versions" > "$TMPRESP"
check_http 200 "GET /documents/$DOC_ID/versions" "$TMPRESP"

curl -s -i "$API/documents/$DOC_ID/content" > "$TMPRESP"
check_http 200 "GET /documents/$DOC_ID/content" "$TMPRESP"

echo "Hallo Welt!" > tmpcontent.txt
curl -s -i -X PUT "$API/documents/$DOC_ID/content?mimeType=text/plain" \
  -H "accept: */*" \
  -H "Content-Type: application/octet-stream" \
  --data-binary @tmpcontent.txt > "$TMPRESP"
rm -f tmpcontent.txt

curl -s -i -X DELETE "$API/documents/$DOC_ID" -H "accept: */*" > "$TMPRESP"
check_http 200 "DELETE /documents/$DOC_ID" "$TMPRESP"

BAD_DOC_JSON="{\"documentId\":\"bad-$DOC_ID\",\"parentFolderId\":null,\"name\":\"BadDoc.txt\",\"contentLength\":10,\"mimeType\":\"text/plain\"}"
curl -s -i -X POST "$API/documents" -H "accept: */*" -H "Content-Type: application/json" -d "$BAD_DOC_JSON" > "$TMPRESP"
check_http 400 "POST /documents (no parentFolderId, should fail)" "$TMPRESP"

#### 4. Metadata
META_DOC_ID="meta-doc-$RAND"
META_FOLDER_ID="meta-folder-$RAND"

section "Metadata (CRUD)"
# Eindeutige IDs für den Metadaten-Test
META_FOLDER_ID="meta-folder-$RAND"
META_DOC_ID="meta-doc-$RAND"
META_ID="meta-$RAND"

# 1. Folder für den Metadata-Test anlegen
FOLDER_JSON="{\"folderId\":\"$META_FOLDER_ID\",\"parentFolderId\":null,\"name\":\"MetaFolder_$RAND\"}"
curl -s -i -X POST "$API/folders" -H "accept: */*" -H "Content-Type: application/json" -d "$FOLDER_JSON" > "$TMPRESP"
check_http 200 "POST /folders ($META_FOLDER_ID)" "$TMPRESP"

# 2. Document im Test-Folder anlegen
DOC_JSON="{\"documentId\":\"$META_DOC_ID\",\"parentFolderId\":\"$META_FOLDER_ID\",\"name\":\"MetaDoc_$RAND.txt\",\"contentLength\":11,\"mimeType\":\"text/plain\"}"
curl -s -i -X POST "$API/documents" -H "accept: */*" -H "Content-Type: application/json" -d "$DOC_JSON" > "$TMPRESP"
check_http 200 "POST /documents ($META_DOC_ID)" "$TMPRESP"

# 3. Jetzt die Metadata-Tests:
META_JSON="{\"metadataId\":\"$META_ID\",\"documentId\":\"$META_DOC_ID\",\"key\":\"author\",\"value\":\"Bernhard $RAND\"}"
curl -s -i -X POST "$API/metadata" -H "accept: */*" -H "Content-Type: application/json" -d "$META_JSON" > "$TMPRESP"
check_http 200 "POST /metadata ($META_ID)" "$TMPRESP"

curl -s -i "$API/metadata/$META_ID" > "$TMPRESP"
check_http 200 "GET /metadata/$META_ID" "$TMPRESP"

META_UPDATE_JSON="{\"metadataId\":\"$META_ID\",\"documentId\":\"$META_DOC_ID\",\"key\":\"author\",\"value\":\"Bernhard Brunner $RAND\"}"
curl -s -i -X PUT "$API/metadata/$META_ID" -H "accept: */*" -H "Content-Type: application/json" -d "$META_UPDATE_JSON" > "$TMPRESP"
check_http 200 "PUT /metadata/$META_ID" "$TMPRESP"

curl -s -i "$API/documents/$META_DOC_ID/metadata" > "$TMPRESP"
check_http 200 "GET /documents/$META_DOC_ID/metadata" "$TMPRESP"

curl -s -i -X DELETE "$API/metadata/$META_ID" -H "accept: */*" > "$TMPRESP"
check_http 200 "DELETE /metadata/$META_ID" "$TMPRESP"


#### 5. Relationship
section "Relationship"
REL_JSON="{\"relationshipId\":\"$REL_ID\",\"sourceId\":\"$DOC_ID\",\"targetId\":\"$FOLDER_ID\",\"type\":\"relatedTo\"}"
curl -s -i -X POST "$API/relationships" -H "accept: */*" -H "Content-Type: application/json" -d "$REL_JSON" > "$TMPRESP"
check_http 200 "POST /relationships" "$TMPRESP"

curl -s -i "$API/relationships/$REL_ID" > "$TMPRESP"
check_http 200 "GET /relationships/$REL_ID" "$TMPRESP"

curl -s -i -X DELETE "$API/relationships/$REL_ID" -H "accept: */*" > "$TMPRESP"
check_http 200 "DELETE /relationships/$REL_ID" "$TMPRESP"

curl -s -i "$API/objects/$DOC_ID/relationships/all" > "$TMPRESP"
check_http 200 "GET /objects/$DOC_ID/relationships/all" "$TMPRESP"

#### 6. Policy
section "Policy"
POL_JSON="{\"policyId\":\"$POL_ID\",\"name\":\"TestPolicy_$RAND\"}"
curl -s -i -X POST "$API/policies" -H "accept: */*" -H "Content-Type: application/json" -d "$POL_JSON" > "$TMPRESP"
check_http 200 "POST /policies" "$TMPRESP"

curl -s -i "$API/policies/$POL_ID" > "$TMPRESP"
check_http 200 "GET /policies/$POL_ID" "$TMPRESP"

curl -s -i -X DELETE "$API/policies/$POL_ID" -H "accept: */*" > "$TMPRESP"
check_http 200 "DELETE /policies/$POL_ID" "$TMPRESP"

curl -s -i -X POST "$API/objects/$DOC_ID/policies/$POL_ID/apply" -H "accept: */*" > "$TMPRESP"
check_http 200 "POST /objects/$DOC_ID/policies/$POL_ID/apply" "$TMPRESP"

curl -s -i -X POST "$API/objects/$DOC_ID/policies/$POL_ID/remove" -H "accept: */*" > "$TMPRESP"
check_http 200 "POST /objects/$DOC_ID/policies/$POL_ID/remove" "$TMPRESP"

#### 7. ACL
section "ACL"
ACL_JSON="{\"aclId\":\"$ACL_ID\",\"principal\":\"admin\",\"permissions\":[\"cmis:read\",\"cmis:write\"]}"
curl -s -i "$API/objects/$DOC_ID/acl" > "$TMPRESP"
check_http 200 "GET /objects/$DOC_ID/acl" "$TMPRESP"
curl -s -i -X PUT "$API/objects/$DOC_ID/acl" \
  -H "accept: */*" \
  -H "Content-Type: application/json" \
  -d "$ACL_JSON" > "$TMPRESP"
check_http 200 "PUT /objects/$DOC_ID/acl" "$TMPRESP"

#### 8. Retention
section "Retention"
RET_JSON="{\"retentionId\":\"$RET_ID\",\"objectId\":\"$DOC_ID\",\"retentionPolicy\":\"7days\"}"
curl -s -i -X POST "$API/retentions" -H "accept: */*" -H "Content-Type: application/json" -d "$RET_JSON" > "$TMPRESP"
check_http 200 "POST /retentions" "$TMPRESP"
curl -s -i "$API/retentions/$RET_ID" > "$TMPRESP"
check_http 200 "GET /retentions/$RET_ID" "$TMPRESP"
curl -s -i -X DELETE "$API/retentions/$RET_ID" -H "accept: */*" > "$TMPRESP"
check_http 200 "DELETE /retentions/$RET_ID" "$TMPRESP"
curl -s -i "$API/objects/$DOC_ID/retentions" > "$TMPRESP"
check_http 200 "GET /objects/$DOC_ID/retentions" "$TMPRESP"

#### 9. ChangeLog
section "ChangeLog"
curl -s -i "$API/changelog" > "$TMPRESP"
check_http 200 "GET /changelog" "$TMPRESP"
curl -s -i "$API/changelog/changes" > "$TMPRESP"
check_http 200 "GET /changelog/changes" "$TMPRESP"

#### 10. TypeDefinition
section "TypeDefinition"
curl -s -i "$API/types/children?typeId=document" > "$TMPRESP"
check_http 200 "GET /types/children?typeId=document" "$TMPRESP"
curl -s -i "$API/types/document" > "$TMPRESP"
check_http 200 "GET /types/document" "$TMPRESP"

rm -f "$TMPRESP"

echo -e "\n==== TEST SUMMARY ====" | tee -a "$LOG"
echo "OK: $successes   FAIL: $failures" | tee -a "$LOG"
if [ $failures -eq 0 ]; then
  echo "==== CMIS TEST SUCCESSFUL ====" | tee -a "$LOG"
else
  echo "==== CMIS TEST FINISHED WITH ERRORS! ====" | tee -a "$LOG"
fi

exit $failures
