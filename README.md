# Chat Room Application

## Overview

The Chat Room Application is a simple and reusable chat room system built using WebSocket, STOMP, SockJS, and MongoDB. It allows users to send and receive messages in real-time within chat rooms.

## Features

- Real-time messaging using WebSocket and STOMP protocol.
- Creation and management of chat rooms.
- Message broadcasting to all users in a chat room.
- Persistent storage of messages in MongoDB.

## Technologies Used

- **Spring Boot:** Backend framework for Java.
- **WebSocket:** Protocol for real-time communication.
- **STOMP:** Messaging protocol for WebSocket.
- **SockJS:** WebSocket fallback options for browsers that don’t support WebSocket.
- **MongoDB:** NoSQL database for storing chat rooms and messages.

## Getting Started

### Prerequisites

- Java 11 or later
- Maven
- MongoDB

### Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/chat-room-application.git
   ```

2. Navigate to the project directory:
   ```bash
   cd chat-room-application
   ```

3. Build the project using Maven:
   ```bash
   mvn clean install
   ```

4. Run the application:
   ```bash
   mvn spring-boot:run
   ```

5. Access the application at `http://localhost:8080`.

## Usage

1. Create a new chat room by sending a POST request to `/rooms/{roomId}`.
2. Join the chat room by connecting to the WebSocket endpoint at `/chat`.
3. Send messages by publishing to the `/app/chat/{roomId}` destination.
4. Receive messages by subscribing to `/topic/messages/{roomId}`.

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please open an issue or submit a pull request for improvements or bug fixes.

## Contact

For any inquiries, please contact [soubhagyamohapatra.bbsr@gmail.com](mailto:soubhagyamohapatra.bbsr@gmail.com).
