'use strict';

// Declare app level module which depends on views, and components
(function(){
    angular.module('taughtology', [
            'ngRoute',
            'ngResource',
            'ngSanitize',
            'LocalStorageModule',
            'ui.bootstrap'
        ])

        .value('broadcontext', {})


        .config(['$routeProvider', '$httpProvider', function($routeProvider, $httpProvider) {


            $httpProvider.interceptors.push('broadEcosContextInterceptor');

            $routeProvider
                .when('/index', {
                    templateUrl: 'app/view/index.html',
                    controller: 'IndexCtrl',
                    reloadOnSearch: true
                })
                .when('/dashboard', {
                    templateUrl: 'app/view/dashboard.html',
                    controller: 'DashboardCtrl'
                })
                .when('/send-challenge/:id', {
                    templateUrl: 'app/view/send-challenge.html',
                    controller: 'SendChallengeCtrl',
                    resolve: {
                        "challengeQuestion" : ['$q', '$route', "Question", function($q, $route, Question){
                            var deferred = $q.defer();
                            var  id = $route.current.params.id;
                            Question.get({id:id},function(question){
                                deferred.resolve(question);
                            });
                            return deferred.promise;
                        }]
                    }
                })
                .when('/question', {
                    templateUrl: 'app/view/question.html',
                    controller: 'QuestionCtrl',
                    resolve: {
                        "randomQuestion" : ['$q', "Question", function($q, Question){
                            var deferred = $q.defer();
                            Question.random({},function(question){
                                deferred.resolve(question)
                            });
                            return deferred.promise;
                        }]
                    }
                })
                .when('/challenge/:id', {
                    templateUrl: 'app/view/challenge.html',
                    controller: 'ChallengeCtrl',
                    resolve: {
                        "challenge" : ['$q', '$route', "Challenge", function($q, $route, Challenge){
                            var  id = $route.current.params.id;
                            var deferred = $q.defer();
                            Challenge.get({id:id},function(challenge){
                                deferred.resolve(challenge);
                            });
                            return deferred.promise;
                        }]
                    }
                })
                .when('/answer-feedback/:id', {
                    templateUrl: 'app/view/answer-feedback.html',
                    controller: 'AnswerFeedbackCtrl',
                    resolve: {
                        "answer" : ['$q', '$route', "Answer", function($q, $route, Answer){
                            var  id = $route.current.params.id;
                            var deferred = $q.defer();
                            Answer.get({id:id},function(answer){
                                deferred.resolve(answer)
                            });
                            return deferred.promise;
                        }]
                    }
                })
                .when('/notifications', {
                    templateUrl: 'app/view/notification-list.html',
                    controller: 'NotificationListCtrl',
                    resolve: {
                        "currentUser" : ['$q', "userService", function($q, userService){
                            var deferred = $q.defer();
                            userService.contextUser().then(function(user){
                                deferred.resolve(user)
                            });
                            return deferred.promise;
                        }]
                    }
                });
            $routeProvider.otherwise({redirectTo: '/index'});
        }]);
})();