const API = '/api';

function getToken() {
  return sessionStorage.getItem('cc_token');
}

function logout() {
  sessionStorage.clear();
  window.location.href = '/login';
}

async function api(path, options = {}) {
  const headers = new Headers(options.headers || {});
  if (!headers.has('Content-Type') && options.body) headers.set('Content-Type', 'application/json');
  const token = getToken();
  if (token) headers.set('Authorization', `Bearer ${token}`);

  const response = await fetch(API + path, { ...options, headers });
  if (response.status === 401 || response.status === 403) throw new Error('AUTH_ERROR');
  const text = await response.text();
  let body = null;
  try { body = text ? JSON.parse(text) : null; } catch (_) { body = text; }
  if (!response.ok) {
    throw new Error(body?.message || body?.error || body || 'Request failed');
  }
  return body;
}

function showNotice(id, message, type = '') {
  const el = document.getElementById(id);
  if (!el) return;
  el.textContent = message;
  el.className = `notice ${type}`.trim();
  if (!message) el.classList.add('hidden');
}

function safe(value) {
  return value === null || value === undefined ? '' : String(value).replace(/[&<>"']/g, c => ({'&':'&amp;','<':'&lt;','>':'&gt;','"':'&quot;',"'":'&#39;'}[c]));
}

function sectorLabel(value) {
  return (value || '').replaceAll('_', ' ').replace(/\b\w/g, m => m.toUpperCase());
}
