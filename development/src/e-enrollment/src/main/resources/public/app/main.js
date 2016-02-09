
var PARTICIPANT_TEMPLATE = '<li id="$id">'
        +'<div class="listrap-toggle">'
            +'<span></span>'
            +'<img style="width: 60px" src="$image" class="img-circle" />'
        +'</div>'
        +'<strong >$name</strong>'
    +'</li>';


function loadContext() {

    var qs = (function(a) {
        if (a == "") return {};
        var b = {};
        for (var i = 0; i < a.length; ++i)
        {
            var p=a[i].split('=', 2);
            if (p.length == 1)
                b[p[0]] = "";
            else
                b[p[0]] = decodeURIComponent(p[1].replace(/\+/g, " "));
        }
        return b;
    })(window.location.search.substr(1).split('&'));

    return {
        'token':qs['token'],
        'platform': decodeURIComponent(qs['platform'])
    };
}


function init() {
    var context = loadContext();
    $.ajaxSetup({
        beforeSend: function(xhr) {
            xhr.setRequestHeader('broad-ecos-token', context.token);
            xhr.setRequestHeader('broad-ecos-platform', context.platform);
            xhr.setRequestHeader('Content-Type', 'application/json');
        }
    });
}

function getAbleToEnrollParticipants () {
    return $.get('/participants/able-to-enroll');
}

function assembleTemplate(participant) {
    return PARTICIPANT_TEMPLATE
        .replace('$id', participant.id)
        .replace('$name', participant.firstName+' '+participant.lastName)
        .replace('$image', participant.picture || 'http://placehold.it/60x60');
}

function generateParticipantList(participants) {
    var templates = [];
    $.each(participants, function(i, participant){
        templates.push(assembleTemplate(participant));
    });
    return templates.join('\n');
}

function updateParticipantsList(participants) {
    var html = '<p class="bg-info">N&atilde;o existem participantes dispon&iacute;veis para matr&iacute;cula.</p>';
    if (participants && participants.length != 0) {
        html = generateParticipantList(participants);
    }

    $('#participantList').html(html);

    $(".listrap").listrap().on("selection-changed", function (event, selection) {
        console.log(selection);
    });

    return participants;
}



function getCourseInfo() {
    return $.get('/broad/course-info');
}

function updateCourseInfo(courseInfo) {
    $('#courseName').html(courseInfo.fullName);
    $('#courseDescription').html(courseInfo.summary);
    $('#courseId').html(courseInfo.id);
    return courseInfo;
}

function postEnrollment(course, participants){
    return $.ajax({
            type: "POST",
            url: '/enroll',
            data: JSON.stringify({courseId: course, participantIds: participants}),
            dataType: 'json'
        });
}

function enroll(){
    var ids = [];
    $(".listrap li.active").each(function(i, $participant){
        ids.push($participant.id);
    });

    if (!ids) {
         alert("Selecione um participante para continuar.")
    } else {
        postEnrollment( $('#courseId').html(), ids);
    }
}


$(function(){

    init();
    getCourseInfo()
        .then(updateCourseInfo);

    getAbleToEnrollParticipants()
        .then(updateParticipantsList);

});