import './App.css';
import { HashRouter, Routes, Route } from "react-router-dom";
import { Menu } from "./components/Menu";
import { RoomList } from './pages/rooms/RoomList';
import { ModuleList } from './pages/modules/ModuleList';
import { ShiftList } from './pages/shifts/ShiftList';

function App() {
  return (
    <div className="App">
      <HashRouter>
        <Menu />

        <Routes>
          <Route path="/"></Route>
          <Route path="/teachers" ></Route>
          <Route path="/groups" ></Route>
          <Route path="/modules" element={<ModuleList />}></Route>
          <Route path="/subjects"></Route>
          <Route path="/shifts" element={<ShiftList />}></Route>
          <Route path="/rooms" element={<RoomList />}></Route>
        </Routes>
      </HashRouter>
    </div>
  );
}

export default App;
