<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCompass - Register</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body class="auth-page">
<div class="auth-card">
    <div class="brand">Career<span>Compass</span></div>
    <p class="subtitle">Create a student profile and discover opportunities across five career sectors.</p>

    <form id="registerForm">
        <label>Full name</label>
        <input id="fullName" required placeholder="Your name">

        <label>Email</label>
        <input id="email" type="email" required placeholder="you@example.com">

        <label>Password</label>
        <input id="password" type="password" minlength="6" required placeholder="At least 6 characters">

        <button class="btn btn-primary full" type="submit">Create account</button>
    </form>

    <div id="message" class="notice hidden"></div>
    <div class="footer-note">Already have an account? <a class="link" href="${pageContext.request.contextPath}/login">Sign in</a></div>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script>
document.getElementById('registerForm').addEventListener('submit', async function(e) {
  e.preventDefault();
  showNotice('message', 'Creating your account...');
  try {
    await api('/auth/register', {
      method: 'POST',
      body: JSON.stringify({
        fullName: document.getElementById('fullName').value,
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
      })
    });
    showNotice('message', 'Account created. Redirecting to login...', 'success');
    setTimeout(() => window.location.href = '/login', 700);
  } catch (error) {
    showNotice('message', error.message, 'error');
  }
});
</script>
</body>
</html>
