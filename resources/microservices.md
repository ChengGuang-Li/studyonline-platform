### The included microservices are as follows:

| Microservice Name | Introduction  |
| :-- | :-: |
| Content Management Service | This pertains to the management of course-related information on the platform. Functions include course search, course addition, course publication, course preview, course planning, and more. To publish a course, one needs to fill in basic course information, marketing details, course planning information, faculty information, and so on. |
| Media Asset Management Service | To effectively manage course media resources, including access and storage of course videos, course images, and files, we have chosen to adopt the distributed file system MinIO as our object storage service to ensure the system's high availability and stability. |
| Search Service | Responsible for implementing full-text search to enhance system search efficiency and matching accuracy, ensuring efficient retrieval and precise matching. |
| Order Payment Service | Responsible for the payment-related functionalities of products, such as purchasing courses, renewing services, buying materials, integrating with third-party payment platforms, handling asynchronous payment notifications, and managing payment failure scenarios. |
| Learning Center Service | Responsible for online learning, course selection, adding courses to my schedule, checking eligibility for learning, and accessing learning videos. |
| System Management Service | Mainly responsible for the maintenance of system parameters, such as the maintenance of system dictionary items. |
| Authentication Authorization Service | Responsible for user registration, login, identity authentication, and authorization management in the project. Implementing system access control based on Spring Security, OAuth 2.0, and JWT modes, and utilizing Resource-Based Access Control for fine-grained authorization management. |
| Gateway Service | The gateway service is responsible for handling and forwarding project requests, as well as performing identity authentication. Only authenticated users are allowed to access protected resources. |
| Registration Center Service | Implementing service registration and discovery with Nacos involves services registering their information with Nacos upon startup. Other services or clients can dynamically discover and invoke available services by querying the service list from Nacos. |
| Configuration Center Service | Implementing dynamic configuration updates and centralized configuration management, services can register various configuration information with the Nacos Configuration Center. It supports real-time monitoring of configuration changes, allowing dynamic updates during runtime without the need to restart the application. Additionally, Nacos provides version control, facilitating easy management and rollback of configurations |
| Captcha Service | Implementing the functionality for sending and verifying verification codes in the project, including image-based CAPTCHAs and email-based codes. Image CAPTCHAs are employed for human-machine verification in the user interface, while email codes are sent via electronic mail for user identity verification.  |