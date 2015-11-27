package grooscript

import org.grooscript.asts.GsNative

class Controllers { 
    @GsNative
    getAngular() {
        /* return window.angular; */
    }
    def init() {
        def controllers = angular.module('myAppControllers', ['myAppServices'])
        controllers.controller('BackgroundJobCtrl', { $scope, BackgroundJobService ->
            $scope.backgroundJobs = BackgroundJobService.get()
        })
    }
    

}