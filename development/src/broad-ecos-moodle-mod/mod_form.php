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
 * The main broadecosmod configuration form
 *
 * It uses the standard core Moodle formslib. For more info about them, please
 * visit: http://docs.moodle.org/en/Development:lib/formslib.php
 *
 * @package    mod_broadecosmod
 * @copyright  2015 Your Name
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */

defined('MOODLE_INTERNAL') || die();

require_once($CFG->dirroot.'/course/moodleform_mod.php');



/**
 * Module instance settings form
 *
 * @package    mod_broadecosmod
 * @copyright  2015 Your Name
 * @license    http://www.gnu.org/copyleft/gpl.html GNU GPL v3 or later
 */
class mod_broadecosmod_mod_form extends moodleform_mod {

    /**
     * Defines forms elements
     */
    public function definition() {
        global $DB;

        $mform = $this->_form;
        // Adding the "general" fieldset, where all the common settings are showed.
        $mform->addElement('header', 'general', get_string('general', 'form'));

        // Adding the standard "name" field.
        $mform->addElement('text', 'name', get_string('broadecosmodname', 'broadecosmod'), array('size' => '64'));
        if (!empty($CFG->formatstringstriptags)) {
            $mform->setType('name', PARAM_TEXT);
        } else {
            $mform->setType('name', PARAM_CLEAN);
        }
        $mform->addRule('name', null, 'required', null, 'client');
        $mform->addRule('name', get_string('maximumchars', '', 255), 'maxlength', 255, 'client');
        $mform->addHelpButton('name', 'broadecosmodname', 'broadecosmod');


        // Adding the standard "intro" and "introformat" fields.
        $this->standard_intro_elements();


        // Adding the "general" fieldset, where all the common settings are showed.
        $mform->addElement('header', 'serviceconfig', get_string('serviceconfig', 'broadecosmod'));

        $mform->addElement('hidden', 'external_service_id');
        $mform->setType('external_service_id', PARAM_RAW);

        $mform->addElement('hidden', 'external_service_uri');
        $mform->setType('external_service_uri', PARAM_RAW);

        $mform->addElement('hidden', 'external_service_entrypoint');
        $mform->setType('external_service_entrypoint', PARAM_RAW);


        $scopes = array();
        if ($this->_instance) {

            $searchScopes = "
                   SELECT name
                     FROM {broadecosmod_scopes}
                    WHERE broadecosmod_id = ?";

            $scopesRescords =  $DB->get_records_sql($searchScopes, array($this->_instance));

            foreach ($scopesRescords as $scope) {
                array_push($scopes, $scope->name);
            }
        }

        $mform->addElement('hidden', 'broadecos_activity_scopes', implode(',', $scopes));
        $mform->setType('broadecos_activity_scopes', PARAM_RAW);




        // Add standard grading elements.
        $this->standard_grading_coursemodule_elements();

        // Add standard elements, common to all modules.
        $this->standard_coursemodule_elements();

        $this->add_action_buttons();

    }

    function display() {
        $mform = $this->_form;

        // Add standard buttons, common to all modules.]
        $jsfile = dirname(__FILE__).'/static/mod_form.js';
        echo "<script type='text/javascript'>".file_get_contents($jsfile)."</script>";
        $mform->display();
    }
}
