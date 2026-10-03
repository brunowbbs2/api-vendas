#!/usr/bin/env bash
set -euo pipefail

evidencias_dir="$(cd "$(dirname "$0")" && pwd)"
projeto_dir="$(cd "$evidencias_dir/.." && pwd)"
perfil_chromium="$(mktemp -d /tmp/guilherme-china-pdf.XXXXXX)"
trap 'rm -rf -- "$perfil_chromium"' EXIT

chromium \
  --headless \
  --no-sandbox \
  --disable-gpu \
  --user-data-dir="$perfil_chromium" \
  --no-pdf-header-footer \
  --print-to-pdf="$projeto_dir/guilherme_china_DR3_AT.PDF" \
  "file://$evidencias_dir/guilherme_china_DR3_AT.html"

echo "PDF gerado em $projeto_dir/guilherme_china_DR3_AT.PDF"
