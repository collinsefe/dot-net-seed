String env = 'pe'

def githubUrl = 'https://github.com'
def gitCreds = "12ac599-aee0"

job("COLL_${env}-Create-Job"){
    description("This Job is used to create the Node Server and its versioned. Changes should be made through the repo")
    keepDependencies(false)

    multiscm{
        git{
            remote{
                name('origin')
                url ("https://github.com/collinsefe/capgem-app")
                credentials(gitCreds)
            }
            extensions{
                branch('*/dev')
            }
        }
        git{
            remote{
                name('origin')
                url ("git@${githubUrl}:prodigitaluk/medical-app-ci.git")
                credentials(gitCreds)
                
            }
            extensions{
                branch('*/main')
            }
        }
    }

    label('A-Build-Node')

    disabled(false)
    concurrentBuild(false)

    steps{
        shell(readFileFromWorkspace("resources/${env.toUpperCase()}/helloJenkins.sh"))
    }

    publishers{
        downstream("JobName", "SUCCESS")
        triggers{
            downstream("zdb-addReadANDWrite", "SUCCESS")
        }
    }
    wrappers{
        credentialsBinding{
            string("masterpass", "asdjhafh123")
            file("PE_PBE_FILE", "32743646hjsdjhdjgdggds")
        }
        timestamps()
    }
    configure{
        it / 'properties' / 'com.sonyericsson.rebuild.RebuildSettings' {
            'autoRebuild'('false')
            'rebuildDisabled'('false')
        }
    }
}