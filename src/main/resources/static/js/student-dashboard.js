function requireStudent() {
    if (!getToken() || sessionStorage.getItem('cc_role') !== 'STUDENT') {
        window.location.href = '/login';
    }
}

async function loadProfile() {
    const p = await api('/students/me');

    document.getElementById('userName').textContent =
        p.fullName || 'Student';

    document.getElementById('heroName').textContent =
        p.fullName ? p.fullName.split(' ')[0] : 'Student';

    document.getElementById('collegeName').value =
        p.collegeName || '';

    document.getElementById('degree').value =
        p.degree || '';

    document.getElementById('graduationYear').value =
        p.graduationYear || '';

    document.getElementById('cgpa').value =
        p.cgpa ?? '';

    document.getElementById('sector').value =
        p.sector || '';

    document.getElementById('interests').value =
        p.interests || '';

    document.getElementById('githubUrl').value =
        p.githubUrl || '';

    document.getElementById('linkedinUrl').value =
        p.linkedinUrl || '';
}


async function loadSkills() {

    const [all, mine] = await Promise.all([
        api('/skills'),
        api('/students/me/skills')
    ]);

    const selected = new Set(
        mine.map(skill => skill.skillId)
    );

    const availableSkills =
        all.filter(skill => !selected.has(skill.skillId));

    document.getElementById('skillSelect').innerHTML =
        availableSkills.length
            ? availableSkills.map(skill => `
                <option value="${skill.skillId}">
                    ${safe(skill.skillName)}
                </option>
              `).join('')
            : '<option value="">All skills added</option>';

    document.getElementById('mySkills').innerHTML =
        mine.length
            ? mine.map(skill => `
                <span class="chip">
                    ${safe(skill.skillName)}
                </span>
              `).join('')
            : '<span class="muted">No skills added yet.</span>';

    document.getElementById('skillCount').textContent =
        mine.length;
}


async function addSelectedSkill() {

    const id =
        document.getElementById('skillSelect').value;

    if (!id) {
        return;
    }

    try {

        await api(
            '/students/me/skills/' + id,
            {
                method: 'POST'
            }
        );

        showNotice(
            'skillMessage',
            'Skill added successfully.',
            'success'
        );

        await loadSkills();
        await loadRecommendations();

    } catch (error) {

        showNotice(
            'skillMessage',
            error.message,
            'error'
        );
    }
}


function opportunityCard(item, recommendation = false) {

    const opportunity =
        recommendation
            ? item.opportunity
            : item;

    const score =
        recommendation
            ? `
                <span class="badge success">
                    ${Math.round(item.matchScore)}% match
                </span>
              `
            : '';

    const recommendationDetails =
        recommendation
            ? `
                <div class="notice">
                    ${safe(item.reason || '')}

                    ${
                        item.missingSkills &&
                        item.missingSkills.length
                            ? `
                                <br>
                                <strong>Missing skills:</strong>
                                ${item.missingSkills
                                    .map(skill => safe(skill))
                                    .join(', ')}
                              `
                            : ''
                    }
                </div>
              `
            : '';

    const requiredSkills =
        opportunity.requiredSkills
            ? opportunity.requiredSkills
                .split(',')
                .map(skill => skill.trim())
                .filter(Boolean)
                .map(skill => `
                    <span class="chip">
                        ${safe(skill)}
                    </span>
                `)
                .join('')
            : '';

    return `
        <div class="card op-card">

            <div class="meta">

                <span class="badge">
                    ${sectorLabel(opportunity.sector)}
                </span>

                ${score}

            </div>

            <div>

                <div class="company">
                    ${safe(opportunity.companyName)}
                </div>

                <div class="title">
                    ${safe(opportunity.title)}
                </div>

            </div>

            <div class="muted">

                ${safe(
                    opportunity.location ||
                    'Location flexible'
                )}

                ·

                Min CGPA
                ${opportunity.minimumCgpa ?? '—'}

            </div>

            <div>
                ${safe(opportunity.description || '')}
            </div>

            <div class="chips">
                ${requiredSkills}
            </div>

            ${recommendationDetails}

            <div class="footer">

                <small class="muted">
                    Deadline:
                    ${safe(opportunity.deadline || 'Open')}
                </small>

                <button
                    class="btn btn-primary btn-small"
                    onclick="applyTo(${opportunity.opportunityId})">

                    Apply

                </button>

            </div>

        </div>
    `;
}


async function loadRecommendations() {

    try {

        const data =
            await api('/recommendations');

        document.getElementById('recommendations').innerHTML =
            data.length
                ? data
                    .map(item =>
                        opportunityCard(item, true))
                    .join('')
                : `
                    <div class="notice">
                        Complete your sector, CGPA and skills
                        to receive recommendations.
                    </div>
                  `;

    } catch (error) {

        showNotice(
            'pageMessage',
            error.message,
            'error'
        );
    }
}


async function loadOpportunities() {

    try {

        const selectedSector =
            document.getElementById(
                'opportunityFilter'
            ).value;

        const data =
            await api('/opportunities');

        const filtered =
            selectedSector
                ? data.filter(
                    opportunity =>
                        opportunity.sector === selectedSector
                  )
                : data;

        document.getElementById('opportunities').innerHTML =
            filtered.length
                ? filtered
                    .map(opportunity =>
                        opportunityCard(opportunity))
                    .join('')
                : `
                    <div class="notice">
                        No opportunities found for this sector.
                    </div>
                  `;

    } catch (error) {

        showNotice(
            'pageMessage',
            error.message,
            'error'
        );
    }
}


async function applyTo(opportunityId) {

    try {

        await api(
            '/applications/' + opportunityId,
            {
                method: 'POST'
            }
        );

        showNotice(
            'pageMessage',
            'Application submitted successfully.',
            'success'
        );

        await loadApplications();

    } catch (error) {

        showNotice(
            'pageMessage',
            error.message,
            'error'
        );
    }
}


async function loadApplications() {

    try {

        const data =
            await api('/applications/me');

        document.getElementById('applicationsBody').innerHTML =
            data.length
                ? data.map(application => `
                    <tr>

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
                            ${safe(
                                (application.appliedAt || '')
                                    .replace('T', ' ')
                            )}
                        </td>

                    </tr>
                  `).join('')
                : `
                    <tr>
                        <td
                            colspan="4"
                            class="muted">

                            No applications yet.

                        </td>
                    </tr>
                  `;

    } catch (error) {

        showNotice(
            'pageMessage',
            error.message,
            'error'
        );
    }
}


document
    .getElementById('profileForm')
    .addEventListener(
        'submit',
        async function (event) {

            event.preventDefault();

            try {

                await api(
                    '/students/me',
                    {
                        method: 'PUT',

                        body: JSON.stringify({

                            collegeName:
                                document
                                    .getElementById(
                                        'collegeName'
                                    )
                                    .value,

                            degree:
                                document
                                    .getElementById(
                                        'degree'
                                    )
                                    .value,

                            graduationYear:
                                document
                                    .getElementById(
                                        'graduationYear'
                                    )
                                    .value
                                    ? Number(
                                        document
                                            .getElementById(
                                                'graduationYear'
                                            )
                                            .value
                                      )
                                    : null,

                            cgpa:
                                document
                                    .getElementById(
                                        'cgpa'
                                    )
                                    .value
                                    ? Number(
                                        document
                                            .getElementById(
                                                'cgpa'
                                            )
                                            .value
                                      )
                                    : null,

                            sector:
                                document
                                    .getElementById(
                                        'sector'
                                    )
                                    .value || null,

                            interests:
                                document
                                    .getElementById(
                                        'interests'
                                    )
                                    .value,

                            githubUrl:
                                document
                                    .getElementById(
                                        'githubUrl'
                                    )
                                    .value,

                            linkedinUrl:
                                document
                                    .getElementById(
                                        'linkedinUrl'
                                    )
                                    .value
                        })
                    }
                );

                showNotice(
                    'pageMessage',
                    'Profile saved successfully.',
                    'success'
                );

                await loadProfile();
                await loadRecommendations();

            } catch (error) {

                showNotice(
                    'pageMessage',
                    error.message,
                    'error'
                );
            }
        }
    );


(async function initStudentDashboard() {

    requireStudent();

    try {

        await loadProfile();
        await loadSkills();
        await loadRecommendations();
        await loadOpportunities();
        await loadApplications();

    } catch (error) {

        if (error.message === 'AUTH_ERROR') {

            logout();

        } else {

            showNotice(
                'pageMessage',
                error.message,
                'error'
            );
        }
    }

})();