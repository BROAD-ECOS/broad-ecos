'use strict';

(function() {
    var app = angular.module('taughtology');
    app.controller('AnswerFeedbackCtrl', ['$scope', '$routeParams', '$moment', 'answer', 'Achievement', 'Comment', 'userService',  function ($scope, $routeParams, $moment, answer, Achievement, Comment, userService) {


        var levelName = function(level){

            var LEVEL_NAMES = {
                'EASY':'Fácil',
                'MEDIUM':'Médio',
                'HARD': 'Difícil',
                'INSANE':'Insano'
            };

            return LEVEL_NAMES[level] || "?";
        };

        var loadComments = function(page, pageSize){
            $scope.comments = Comment.get({answerId: answer.question.id, page: page ||0, pageSize: pageSize || 10});
        };

        var fromNow = function(time){
            return $moment(time).fromNow();
        };

        var publishComment = function(){

            userService.contextUser().then(function(user){
                new Comment({
                    message:$scope.comment,
                    question: answer.question,
                    author: user
                }).$save(function(){
                    $scope.comment = '';
                    loadComments();
                });
            });

        };

        loadComments();


        $scope.answer = answer;
        $scope.levelName = levelName(answer.question.level);


        $scope.correctAnswerClass = answer.question.solution ? 'label-success' : 'label-danger';
        $scope.correctAnswerMessage = answer.question.solution ? 'Verdadeiro' : 'Falso';
        $scope.answeredClass = answer.answeredOption ? 'label-success' : 'label-danger';
        $scope.answeredMessage = answer.answeredOption ? 'Verdadeiro' : 'Falso';

        $scope.achievement = Achievement.get({answerId:answer.id});

        $scope.publishComment = publishComment;

        $scope.fromNow = fromNow;

    }]);
})();