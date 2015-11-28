package grooscript

import org.grooscript.asts.GsNative

class Services {
    @GsNative
    getAngular() {
        /* return window.angular; */
    }
    def init() {
        def service = angular.module('myAppServices', ['ngResource'])
        
        service.factory('BackgroundJobService', { $resource -> 
            $resource('get')
        })
    }
}
