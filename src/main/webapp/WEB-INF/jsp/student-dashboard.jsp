<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCompass - Student</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="layout">
    <header class="topbar">
        <div class="brand">Career<span>Compass</span></div>
        <div class="top-actions">
            <span id="userName" class="muted"></span>
            <button class="btn btn-secondary btn-small" onclick="logout()">Logout</button>
        </div>
    </header>

    <main class="container">
        <section class="hero">
            <h1>Welcome, <span id="heroName">Student</span></h1>
            <p>Explore opportunities across technology, engineering, design, sales and business operations.</p>
        </section>

        <div id="pageMessage" class="notice hidden"></div>

        <section class="grid grid-2">
            <div class="card">
                <div class="section-title"><h2>Your profile</h2><span class="badge">Student</span></div>
                <form id="profileForm">
                    <div class="form-grid">
                        <div><label>College</label><input id="collegeName"></div>
                        <div><label>Degree / Course</label><input id="degree"></div>
                        <div><label>Graduation year</label><input id="graduationYear" type="number"></div>
                        <div><label>CGPA</label><input id="cgpa" type="number" step="0.01"></div>
                        <div><label>Sector</label>
                            <select id="sector">
                                <option value="">Select sector</option>
                                <option value="TECHNOLOGY">Technology</option>
                                <option value="CORE_ENGINEERING">Core Engineering</option>
                                <option value="DESIGN_CREATIVE">Design & Creative</option>
                                <option value="SALES_MARKETING">Sales & Marketing</option>
                                <option value="BUSINESS_OPERATIONS">Business & Operations</option>
                            </select>
                        </div>
                        <div><label>Interests</label><input id="interests" placeholder="design, creator, analytics"></div>
                        <div><label>GitHub</label><input id="githubUrl"></div>
                        <div><label>LinkedIn</label><input id="linkedinUrl"></div>
                    </div>
                    <button class="btn btn-primary full" type="submit">Save profile</button>
                </form>
            </div>

            <div class="card">
                <div class="section-title"><h2>Your skills</h2><span class="badge" id="skillCount">0</span></div>
                <div id="mySkills" class="chips" style="margin-bottom:15px;"></div>
                <label>Add a skill</label>
                <select id="skillSelect"></select>
                <button class="btn btn-secondary full" onclick="addSelectedSkill()">Add skill</button>
                <div id="skillMessage" class="notice hidden"></div>
            </div>
        </section>

        <section class="card" style="margin-top:16px;">
            <div class="section-title">
                <div><h2>Recommended for you</h2><div class="muted">A simple explainable match based on sector, interests, skills and eligibility.</div></div>
                <button class="btn btn-primary btn-small" onclick="loadRecommendations()">Refresh</button>
            </div>
            <div id="recommendations" class="grid grid-3"></div>
        </section>

        <section class="card" style="margin-top:16px;">
            <div class="section-title">
                <div><h2>All opportunities</h2><div class="muted">Browse the combined opportunity pool across sectors.</div></div>
                <select id="opportunityFilter" style="width:220px;" onchange="loadOpportunities()">
                    <option value="">All sectors</option>
                    <option value="TECHNOLOGY">Technology</option>
                    <option value="CORE_ENGINEERING">Core Engineering</option>
                    <option value="DESIGN_CREATIVE">Design & Creative</option>
                    <option value="SALES_MARKETING">Sales & Marketing</option>
                    <option value="BUSINESS_OPERATIONS">Business & Operations</option>
                </select>
            </div>
            <div id="opportunities" class="grid grid-3"></div>
        </section>

        <section class="card" style="margin-top:16px;">
            <div class="section-title"><h2>My applications</h2><button class="btn btn-secondary btn-small" onclick="loadApplications()">Refresh</button></div>
            <div class="table-wrap">
                <table>
                    <thead><tr><th>Opportunity</th><th>Company</th><th>Status</th><th>Applied</th></tr></thead>
                    <tbody id="applicationsBody"></tbody>
                </table>
            </div>
        </section>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script src="${pageContext.request.contextPath}/js/student-dashboard.js"></script>
</body>
</html>
