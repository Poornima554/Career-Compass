<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCompass - Login</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body class="auth-page">
<div class="auth-card">
    <div class="brand">Career<span>Compass</span></div>
    <p class="subtitle">Find opportunities that fit your skills, interests and career direction.</p>

    <form id="loginForm">
        <label>Email</label>
        <input id="email" type="email" required placeholder="you@example.com">

        <label>Password</label>
        <input id="password" type="password" required placeholder="••••••••">

        <button class="btn btn-primary full" type="submit">Sign in</button>
    </form>

    <div id="message" class="notice hidden"></div>
    <div class="footer-note">New here? <a class="link" href="${pageContext.request.contextPath}/register">Create a student account</a></div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
document.getElementById('loginForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  showNotice('message', 'Signing in...');
  try {
    const data = await api('/auth/login', {
      method: 'POST',
      body: JSON.stringify({
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
      })
    });
    sessionStorage.setItem('cc_token', data.token);
    sessionStorage.setItem('cc_role', data.role);
    sessionStorage.setItem('cc_name', data.fullName);
    window.location.href = data.role === 'ADMIN' ? '/admin' : '/student';
  } catch (error) {
    showNotice('message', error.message === 'AUTH_ERROR' ? 'Invalid credentials.' : error.message, 'error');
  }
});
</script>
</body>
</html>
