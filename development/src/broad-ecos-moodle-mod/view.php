<?php
// This file is part of Moodle - http://moodle.org/
//
// Moodle is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// Moodle is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with Moodle.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Prints a particular instance of broadecosmod
 *
 * You can have a rather longer description of the file as well,
 * if you like, and it can span multiple lines.
 *
 * @package    mod_broadecosmod
 * @copyright  2015 Your Name
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

require_once(dirname(dirname(dirname(__FILE__))).'/config.php');
require_once(dirname(__FILE__).'/lib.php');
require_once(dirname(__FILE__).'/locallib.php');

global $USER;


$id = optional_param('id', 0, PARAM_INT); // Course_module ID, or
$n  = optional_param('n', 0, PARAM_INT);  // ... broadecosmod instance ID - it should be named as the first character of the module.

if ($id) {
    $cm         = get_coursemodule_from_id('broadecosmod', $id, 0, false, MUST_EXIST);
    $course     = $DB->get_record('course', array('id' => $cm->course), '*', MUST_EXIST);
    $broadecosmod  = $DB->get_record('broadecosmod', array('id' => $cm->instance), '*', MUST_EXIST);
} else if ($n) {
    $broadecosmod  = $DB->get_record('broadecosmod', array('id' => $n), '*', MUST_EXIST);
    $course     = $DB->get_record('course', array('id' => $broadecosmod->course), '*', MUST_EXIST);
    $cm         = get_coursemodule_from_instance('broadecosmod', $broadecosmod->id, $course->id, false, MUST_EXIST);
} else {
    error('You must specify a course_module ID or an instance ID');
}

require_login($course, true, $cm);

$event = \mod_broadecosmod\event\course_module_viewed::create(array(
    'objectid' => $PAGE->cm->instance,
    'context' => $PAGE->context,
));
$event->add_record_snapshot('course', $PAGE->course);
$event->add_record_snapshot($PAGE->cm->modname, $broadecosmod);
$event->trigger();

// Print the page header.

$PAGE->set_url('/mod/broadecosmod/view.php', array('id' => $cm->id));
$PAGE->set_title(format_string($broadecosmod->name));
$PAGE->set_heading(format_string($course->fullname));

/*
 * Other things you may want to set - remove if not needed.
 * $PAGE->set_cacheable(false);
 * $PAGE->set_focuscontrol('some-html-id');
 * $PAGE->add_body_class('broadecosmod-'.$somevar);
 */
$token = null;
$cookieId = 'broadecos.token.'.$broadecosmod->external_service_id;
$cookieName = str_replace('.','_',$cookieId);
if (!isset($_COOKIE[$cookieName])) {


    $newToken = bin2hex(openssl_random_pseudo_bytes(16));
    setcookie($cookieId, $newToken, time()+3600, '/');

    $searchScopes = "SELECT name
                     FROM {broadecosmod_scopes}
                    WHERE broadecosmod_id = ?";

    $scopes = $DB->get_records_sql($searchScopes, array($broadecosmod->id));
    $scopesNames = array_map(function($s) { return $s->name ;}, $scopes);

    $token = new stdClass();
    $token->token = $newToken;
    $token->participant_id = $USER->id;
    $token->course_id = $course->id;
    $token->service_id = $broadecosmod->external_service_id;
    $token->session_id = session_id();
    $token->approved_scopes =  implode(';', $scopesNames);
    $token->timecreated = time();
    $token->timeupdated = time();

    $token->id = $DB->insert_record('broadecos_token', $token);

} else {
    $sessionToken=$_COOKIE[$cookieName];
    $token = $DB->get_record_sql('SELECT * FROM {broadecos_token} WHERE token  = ? AND timecreated >= ?', array($sessionToken, time()-3600));
    if (!array_key_exists('id', $token)) {
        setcookie($cookieName, null, 1);
        header("Location: ".$PAGE->url);
        die();
    }

}
// Output starts here.
echo $OUTPUT->header();
echo '<div class="singlebutton" style="float: right;">
        <form action="'.$broadecosmod->external_service_entrypoint.'" method="get" target="_blank">
            <div>
                <input type="submit" value="Abrir em outra janela">
                <input type="hidden" value="'.urlencode('http://localhost/moodle/mod/broadecosmod/ws.php').'" name="platform">
                <input type="hidden" value="'.$token->token.'" name="token">
            </div>
        </form>
     </div>';
echo '<hr />';

echo '<iframe src="'.($broadecosmod->external_service_entrypoint)
        .'?token='.$token->token
        .'&platform='.urlencode('http://localhost/moodle/mod/broadecosmod/ws.php')
        .'"  width="100%" height="779px" frameborder="0"></iframe>';

// Finish the page.
echo $OUTPUT->footer();
