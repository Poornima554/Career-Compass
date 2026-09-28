<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>CareerCompass - Admin</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/app.css">
</head>
<body>
<div class="layout">
    <header class="topbar">
        <div class="brand">Career<span>Compass</span></div>
        <div class="top-actions"><span class="badge">Admin</span><button class="btn btn-secondary btn-small" onclick="logout()">Logout</button></div>
    </header>

    <main class="container">
        <section class="hero">
            <h1>Admin dashboard</h1>
            <p>Manage the opportunity pool, skills and applications across all five sectors.</p>
        </section>

        <div id="message" class="notice hidden"></div>

        <section class="grid grid-3">
            <div class="card stat"><div class="label">Students</div><div class="value" id="students">0</div></div>
            <div class="card stat"><div class="label">Opportunities</div><div class="value" id="opportunityCount">0</div></div>
            <div class="card stat"><div class="label">Applications</div><div class="value" id="applicationCount">0</div></div>
        </section>

        <section class="grid grid-2" style="margin-top:16px;">
            <div class="card">
                <div class="section-title"><h2>Add opportunity</h2><span class="badge">Admin CRUD</span></div>
                <form id="opportunityForm">
                    <div class="form-grid">
                        <div><label>Title</label><input id="title" required></div>
                        <div><label>Company</label><input id="companyName" required></div>
                        <div><label>Location</label><input id="location"></div>
                        <div><label>Sector</label>
                            <select id="sector" required>
                                <option value="TECHNOLOGY">Technology</option>
                                <option value="CORE_ENGINEERING">Core Engineering</option>
                                <option value="DESIGN_CREATIVE">Design & Creative</option>
                                <option value="SALES_MARKETING">Sales & Marketing</option>
                                <option value="BUSINESS_OPERATIONS">Business & Operations</option>
                            </select>
                        </div>
                        <div><label>Minimum CGPA</label><input id="minimumCgpa" type="number" step="0.1"></div>
                        <div><label>Deadline</label><input id="deadline" type="date"></div>
                        <div class="span-2"><label>Required skills (comma separated)</label><input id="requiredSkills" placeholder="Java, SQL"></div>
                        <div class="span-2"><label>Tags / interests (comma separated)</label><input id="tags" placeholder="backend, api, technology"></div>
                        <div class="span-2"><label>Description</label><textarea id="description"></textarea></div>
                    </div>
                    <button class="btn btn-primary full" type="submit">Create opportunity</button>
                </form>
            </div>

            <div class="card">
                <div class="section-title"><h2>Manage skills</h2><span class="badge" id="skillCount">0</span></div>
                <label>New skill</label>
                <div style="display:flex;gap:8px;"><input id="newSkill" placeholder="e.g. Canva"><button class="btn btn-primary" onclick="createSkill()">Add</button></div>
                <div id="skills" class="chips" style="margin-top:16px;"></div>
            </div>
        </section>

        <section class="card" style="margin-top:16px;">
            <div class="section-title"><h2>Opportunities</h2><button class="btn btn-secondary btn-small" onclick="loadAdminOpportunities()">Refresh</button></div>
            <div class="table-wrap"><table><thead><tr><th>Role</th><th>Company</th><th>Sector</th><th>Deadline</th><th></th></tr></thead><tbody id="opportunitiesBody"></tbody></table></div>
        </section>

        <section class="card" style="margin-top:16px;">
            <div class="section-title"><h2>Applications</h2><button class="btn btn-secondary btn-small" onclick="loadApplications()">Refresh</button></div>
            <div class="table-wrap"><table><thead><tr><th>Student</th><th>Opportunity</th><th>Company</th><th>Status</th><th>Action</th></tr></thead><tbody id="applicationsBody"></tbody></table></div>
        </section>
    </main>
</div>
<script src="${pageContext.request.contextPath}/js/app.js"></script>
<script src="${pageContext.request.contextPath}/js/admin-dashboard.js"></script>
</body>
</html>
