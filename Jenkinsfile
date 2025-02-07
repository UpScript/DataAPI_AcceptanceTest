pipeline {
    agent any

    parameters {
        string(name: 'GIT_BRANCH', defaultValue: 'main', description: 'Select Git branch')
        choice(name: 'ENVIRONMENT', choices: ['QA', 'UAT'], description: 'Select Environment')
        choice(name: 'CUCUMBER_TAGS', choices: ['@regression', '@dev', '@smoke'], description: 'Select Cucumber Tags')
    }

    stages {

        stage('Validate Parameters') {
                    steps {
                        script {
                            if (!params.GIT_BRANCH?.trim()) {
                                error("GIT_BRANCH is required and cannot be empty.")
                            }
                            if (!params.ENVIRONMENT?.trim()) {
                                error("ENVIRONMENT is required and must be selected.")
                            }
                            if (!params.CUCUMBER_TAGS?.trim()) {
                                error("CUCUMBER_TAGS is required and must be selected.")
                            }
                        }
                    }
        }

        stage('Checkout Code') {
            steps {
                script {
                    git branch: params.GIT_BRANCH, url: 'https://github.com/UpScript/DataAPI_AcceptanceTest.git'
                }
            }
        }

        stage('Verify Jenkinsfile Exists') {
            steps {
                script {
                    sh "ls -l Jenkinsfile || echo 'Jenkinsfile not found!'"
                }
            }
        }

        stage('Run API Tests') {
            steps {
                script {
                    sh "mvn clean test -Dcucumber.filter.tags=${params.CUCUMBER_TAGS} -Denv=${params.ENVIRONMENT}"
                }
            }
        }

        stage('Publish Cucumber Report') {
            steps {
                publishHTML(target: [
                    reportDir: 'target/cucumber-reports',
                    reportFiles: 'cucumber.html',
                    reportName: 'Data API Test Report'
                ])
            }
        }

        stage('Archive Cucumber Report') {
            steps {
                archiveArtifacts allowEmptyArchive: true, artifacts: 'target/cucumber-reports/cucumber.html', followSymlinks: false
            }
        }

    }
}
