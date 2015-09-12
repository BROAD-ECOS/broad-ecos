'use strict';

angular.module('lopl')
.factory('moment', [function () {
    moment.locale('pt-br');
    return moment;
 }]);
