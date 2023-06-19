import React, { useEffect, useState } from 'react';
import axios from 'axios';
import SockJS from 'sockjs-client';
import Stomp from 'stompjs';

const App = () => {
  const [roomId, setRoomId] = useState('');
  const [room, setRoom] = useState({});
  const [sender, setSender] = useState('');
  const [message, setMessage] = useState('');
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    setRoomId(localStorage.getItem('wschat.roomId'));
    setSender(localStorage.getItem('wschat.sender'));
    findRoom();

    // 웹소켓과 Stomp 초기화
    let sock = new SockJS('/ws/chat');
    let ws = Stomp.over(sock);
    let reconnect = 0;

    const connect = () => {
      // pub/sub 이벤트
      ws.connect(
        {},
        (frame) => {
          ws.subscribe(`/topic/chat/room/${roomId}`, (message) => {
            const recv = JSON.parse(message.body);
            recvMessage(recv);
          });
          ws.send(
            '/app/chat/message',
            {},
            JSON.stringify({ type: 'ENTER', roomId, sender })
          );
        },
        (error) => {
          if (reconnect++ <= 5) {
            setTimeout(() => {
              console.log('연결 재시도');
              sock = new SockJS('/ws/chat');
              ws = Stomp.over(sock);
              connect();
            }, 10 * 1000);
          }
        }
      );
    };

    connect();

    // 정리 함수
    return () => {
      ws.disconnect();
    };
  }, []);

  const findRoom = () => {
    axios.get(`/chat/room/${roomId}`).then((response) => {
      setRoom(response.data);
    });
  };

  const sendMessage = () => {
    ws.send(
      '/app/chat/message',
      {},
      JSON.stringify({ type: 'TALK', roomId, sender, message })
    );
    setMessage('');
  };

  const recvMessage = (recv) => {
    setMessages((prevMessages) => [
      {
        type: recv.type,
        sender: recv.type === 'ENTER' ? '[알림]' : recv.sender,
        message: recv.message,
      },
      ...prevMessages,
    ]);
  };

  return (
    <div className="container" id="app">
      <div>
        <h2>{room.name}</h2>
      </div>
      <div className="input-group">
        <div className="input-group-prepend">
          <label className="input-group-text">내용</label>
        </div>
        <input
          type="text"
          className="form-control"
          value={message}
          onChange={(e) => setMessage(e.target.value)}
          onKeyPress={(e) => {
            if (e.key === 'Enter') {
              sendMessage();
            }
          }}
        />
        <div className="input-group-append">
          <button className="btn btn-primary" type="button" onClick={sendMessage}>
            보내기
          </button>
        </div>
      </div>
      <ul className="list-group">
        {messages.map((message, index) => (
          <li className="list-group-item" key={index}>
            {message.sender} - {message.message}
          </li>
        ))}
      </ul>
      <div></div>
    </div>
  );
};

export default App;
