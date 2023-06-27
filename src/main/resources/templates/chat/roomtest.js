import React, { useEffect, useState } from 'react';
import axios from 'axios';

const ChatList = () => {
  const [roomName, setRoomName] = useState('');
  const [chatrooms, setChatrooms] = useState([]);

  useEffect(() => {
    findAllRoom();
  }, []);

  const findAllRoom = () => {
    axios.get('/chat/rooms').then((response) => {
      setChatrooms(response.data);
    });
  };

  const createRoom = () => {
    if (roomName === '') {
      alert('방 제목을 입력해주세요.');
      return;
    } else {
        // 방 개설하는 코드
      const params = new URLSearchParams();
      params.append('name', roomName);
      axios
        .post('/chat/room', params)
        .then((response) => {
          alert(`${response.data.roomName} 방 개설에 성공하였습니다.`);
          setRoomName('');
          findAllRoom();
        })
        .catch((error) => {
          alert('채팅방 개설에 실패하였습니다.');
        });
    }
  };

  const enterRoom = (roomId) => {
    const sender = prompt('대화명을 입력해주세요.');
    if (sender !== '') {
      localStorage.setItem('wschat.sender', sender);
      localStorage.setItem('wschat.roomId', roomId);
      window.location.href = `/chat/room/enter/${roomId}`;
    }
  };

  return (
    <div className="container" id="app">
      <div className="row">
        <div className="col-md-12">
          <h3>채팅방 리스트</h3>
        </div>
      </div>
      <div className="input-group">
        <div className="input-group-prepend">
          <label className="input-group-text">방제목</label>
        </div>
        <input
          type="text"
          className="form-control"
          value={roomName}
          onChange={(e) => setRoomName(e.target.value)}
          onKeyUp={(e) => {
            if (e.key === 'Enter') {
              createRoom();
            }
          }}
        />
        <div className="input-group-append">
          <button className="btn btn-primary" type="button" onClick={createRoom}>
            채팅방 개설
          </button>
        </div>
      </div>
      <ul className="list-group">
        {chatrooms.map((item) => (
          <li
            className="list-group-item list-group-item-action"
            key={item.roomId}
            onClick={() => enterRoom(item.roomId)}
          >
            {item.roomName}
          </li>
        ))}
      </ul>
    </div>
  );
};

export default ChatList;
