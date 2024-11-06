def githubUrl = 'https://github.com/collinsefe/dotnet-app-image.git'
def gitCreds = "prodigital-collinsefe"


def environments = [
    dev: [
        name: "Development",
        branch: "dev"
    ],
    test: [
        name: "Testing",
        branch: "test"
    ],
]

environments.each { env, config ->
pipelineJob("COLL-FRONTEND-${env.toUpperCase()}-Job") {
    description("This Job is used to create the Node Server and is versioned. Changes should be made through the repo.")
    keepDependencies(false)

    definition {
        cpsScm {
            scm {
                git {
                    remote {
                        url(githubUrl)
                        credentials(gitCreds)
                    }
                    branch("*/${config.branch}")
                }
            }
        }
    }
    triggers {
            githubPush()  
        }

    properties {
        disableConcurrentBuilds()
    }
 }
}
