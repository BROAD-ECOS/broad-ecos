'use strict';

(function() {
    var app = angular.module('taughtology');
    app.factory('gameService', ['AchievementSummary', '$q', function (AchievementSummary, $q) {

        var _gameService = {};
        _gameService.me = {};
        _gameService. LEVEL_NAMES = {
            'EASY':'Fácil',
            'MEDIUM':'Médio',
            'HARD': 'Difícil',
            'INSANE':'Insano'
        };

        return {
            levelName : function(level){
                return _gameService.LEVEL_NAMES[level] || "?";
            }
        };

    }]);

})();