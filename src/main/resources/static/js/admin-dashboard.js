/**
 * 
 */function requireAdmin() {

    if (
        !getToken() ||
        sessionStorage.getItem('cc_role') !== 'ADMIN'
    ) {
        window.location.href = '/login';
    }
}


async function loadStats() {

    const stats =
        await api('/admin/stats');

    document.getElementById('students').textContent =
        stats.students;

    document.getElementById('opportunityCount').textContent =
        stats.opportunities;

    document.getElementById('applicationCount').textContent =
        stats.applications;
}


async function loadSkills() {

    const data =
        await api('/skills');

    document.getElementById('skillCount').textContent =
        data.length;

    document.getElementById('skills').innerHTML =
        data.length
            ? data.map(skill => `
                <span class="chip">

                    ${safe(skill.skillName)}

                    <button
                        type="button"
                        style="
                            border:0;
                            background:none;
                            color:#c33;
                            cursor:pointer;
                        "
                        onclick="deleteSkill(${skill.skillId})">

                        ×

                    </button>

                </span>
              `).join('')
            : '<span class="muted">No skills available.</span>';
}


async function createSkill() {

    const input =
        document.getElementById('newSkill');

    const name =
        input.value.trim();

    if (!name) {

        showNotice(
            'message',
            'Please enter a skill name.',
            'error'
        );

        return;
    }

    try {

        await api(
            '/skills?name=' +
            encodeURIComponent(name),
            {
                method: 'POST'
            }
        );

        input.value = '';

        showNotice(
            'message',
            'Skill created successfully.',
            'success'
        );

        await loadSkills();
        await loadStats();

    } catch (error) {

        showNotice(
            'message',
            error.message,
            'error'
        );
    }
}


async function deleteSkill(id) {

    if (!confirm('Delete this skill?')) {
        return;
    }

    try {

        await api(
            '/skills/' + id,
            {
                method: 'DELETE'
            }
        );

        showNotice(
            'message',
            'Skill deleted.',
            'success'
        );

        await loadSkills();
        await loadStats();

    } catch (error) {

        showNotice(
            'message',
            error.message,
            'error'
        );
    }
}


async function loadAdminOpportunities() {

    const data =
        await api('/opportunities');

    document.getElementById(
        'opportunitiesBody'
    ).innerHTML =
        data.length
            ? data.map(opportunity => `
                <tr>

                    <td>
                        ${safe(opportunity.title)}
                    </td>

                    <td>
                        ${safe(opportunity.companyName)}
                    </td>

                    <td>
                        ${safe(
                            sectorLabel(
                                opportunity.sector
                            )
                        )}
                    </td>

                    <td>
                        ${safe(
                            opportunity.deadline || ''
                        )}
                    </td>

                    <td>

                        <button
                            type="button"
                            class="btn btn-danger btn-small"
                            onclick="deleteOpportunity(
                                ${opportunity.opportunityId}
                            )">

                            Delete

                        </button>

                    </td>

                </tr>
              `).join('')
            : `
                <tr>
                    <td
                        colspan="5"
                        class="muted">

                        No opportunities available.

                    </td>
                </tr>
              `;
}


async function deleteOpportunity(id) {

    if (
        !confirm(
            'Delete this opportunity?'
        )
    ) {
        return;
    }

    try {

        await api(
            '/opportunities/' + id,
            {
                method: 'DELETE'
            }
        );

        showNotice(
            'message',
            'Opportunity deleted.',
            'success'
        );

        await loadAdminOpportunities();
        await loadStats();

    } catch (error) {

        showNotice(
            'message',
            error.message,
            'error'
        );
    }
}


async function loadApplications() {

    const data =
        await api('/applications');

    document.getElementById(
        'applicationsBody'
    ).innerHTML =
        data.length
            ? data.map(application => `
                <tr>

                    <td>
                        ${safe(
                            application.studentName
                        )}
                    </td>

                    <td>
                        ${safe(
                            application.opportunityTitle
                        )}
                    </td>

                    <td>
                        ${safe(
                            application.companyName
                        )}
                    </td>

                    <td>

                        <span class="badge">
                            ${safe(
                                sectorLabel(
                                    application.status
                                )
                            )}
                        </span>

                    </td>

                    <td>

                        <select
                            onchange="
                                updateStatus(
                                    ${application.applicationId},
                                    this.value
                                )
                            ">

                            ${
                                [
                                    'APPLIED',
                                    'UNDER_REVIEW',
                                    'INTERVIEW_SCHEDULED',
                                    'SELECTED',
                                    'REJECTED'
                                ]
                                .map(status => `
                                    <option
                                        value="${status}"
                                        ${
                                            status ===
                                            application.status
                                                ? 'selected'
                                                : ''
                                        }>

                                        ${sectorLabel(status)}

                                    </option>
                                `)
                                .join('')
                            }

                        </select>

                    </td>

                </tr>
              `).join('')
            : `
                <tr>

                    <td
                        colspan="5"
                        class="muted">

                        No applications yet.

                    </td>

                </tr>
              `;
}


async function updateStatus(
    applicationId,
    status
) {

    try {

        await api(
            '/applications/' +
            applicationId +
            '/status',
            {
                method: 'PUT',

                body: JSON.stringify({
                    status: status
                })
            }
        );

        showNotice(
            'message',
            'Application status updated.',
            'success'
        );

        await loadApplications();

    } catch (error) {

        showNotice(
            'message',
            error.message,
            'error'
        );
    }
}


document
    .getElementById('opportunityForm')
    .addEventListener(
        'submit',
        async function (event) {

            event.preventDefault();

            try {

                await api(
                    '/opportunities',
                    {
                        method: 'POST',

                        body: JSON.stringify({

                            title:
                                document
                                    .getElementById(
                                        'title'
                                    )
                                    .value,

                            companyName:
                                document
                                    .getElementById(
                                        'companyName'
                                    )
                                    .value,

                            location:
                                document
                                    .getElementById(
                                        'location'
                                    )
                                    .value,

                            sector:
                                document
                                    .getElementById(
                                        'sector'
                                    )
                                    .value,

                            minimumCgpa:
                                document
                                    .getElementById(
                                        'minimumCgpa'
                                    )
                                    .value
                                    ? Number(
                                        document
                                            .getElementById(
                                                'minimumCgpa'
                                            )
                                            .value
                                      )
                                    : null,

                            deadline:
                                document
                                    .getElementById(
                                        'deadline'
                                    )
                                    .value || null,

                            requiredSkills:
                                document
                                    .getElementById(
                                        'requiredSkills'
                                    )
                                    .value,

                            tags:
                                document
                                    .getElementById(
                                        'tags'
                                    )
                                    .value,

                            description:
                                document
                                    .getElementById(
                                        'description'
                                    )
                                    .value
                        })
                    }
                );

                showNotice(
                    'message',
                    'Opportunity created successfully.',
                    'success'
                );

                this.reset();

                await loadAdminOpportunities();
                await loadStats();

            } catch (error) {

                showNotice(
                    'message',
                    error.message,
                    'error'
                );
            }
        }
    );


(async function initAdminDashboard() {

    requireAdmin();

    try {

        await loadStats();
        await loadSkills();
        await loadAdminOpportunities();
        await loadApplications();

    } catch (error) {

        if (error.message === 'AUTH_ERROR') {

            logout();

        } else {

            showNotice(
                'message',
                error.message,
                'error'
            );
        }
    }

})();