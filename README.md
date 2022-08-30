# Mentoring-Assignment-Intermediate



application.properties에 아래 내용 추가해야 s3 이미지 업로드 정상 작동합니다.(비밀키라 깃허브 공유x)
#S3
cloud.aws.credentials.accessKey={accessKey}
cloud.aws.credentials.secretKey={secretKey}
cloud.aws.s3.bucket={bucketName}
cloud.aws.region.static=ap-northeast-2
cloud.aws.stack.auto-=false
cloud.aws.credentials.instanceProfile: true

spring.servlet.multipart.max-file-size: 10MB
spring.servlet.multipart.max-request-size: 10MB
