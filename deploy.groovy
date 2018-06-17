def folderName = 'deploy'
def REGION = 'eu-west-1'
def AWS_SNS_REGION = "eu-west-1"
def env = 'test'
slackNotifierConfig = {
    teamDomain('teamDomain')
    room('#jenkins')
    startNotification(true)
    notifyAborted(true)
    notifyFailure(true)
    notifyNotBuilt(true)
    notifySuccess(true)
    notifyUnstable(true)
    notifyBackToNormal(true)
    notifyRepeatedFailure(true)
    includeTestSummary(false)
    commitInfoChoice('AUTHORS_AND_TITLES')
    includeCustomMessage(false)
    sendAs(null)
    customMessage('')
    authTokenCredentialId(null)
}
	folder(folderName)
    def jobname = "xppeper"
	def bucketname = "bucketname"
    def deployScript = """
    set -x
    set -u
    set -e
	rm -rf /var/lib/jenkins/workspace/mysoruce
	mkdir -p /var/lib/jenkins/workspace/mysoruce
	aws s3 cp s3://$bucketname/codedeploy.zip /var/lib/jenkins/workspace/mysoruce
	cd  /var/lib/jenkins/workspace/mysoruce
	unzip codedeploy.zip
	rm codedeploy.zip
	cd /var/lib/jenkins/workspace/$jobname
	cp -pr  /var/lib/jenkins/workspace/$jobname/*  /var/lib/jenkins/workspace/mysoruce/src

	cd /var/lib/jenkins/workspace/mysoruce
	zip -r -X ../deploy.zip .
	aws s3 cp /var/lib/jenkins/workspace/deploy.zip s3://$bucketname/ --region $REGION
    """.stripIndent()

job(jobname) {
	parameters {
		stringParam('tagName')
	}
	wrappers {
		 timestamps()
	}
	scm {
		git {
			remote {
				name('origin')
				url("https://github.com/xpeppers/cloud-phoenix-kata.git")
			}
			branch('master')
		}
	 }
	steps {
		shell(deployScript)
	}
	publishers {
		slackNotifier(slackNotifierConfig)
	}
}
