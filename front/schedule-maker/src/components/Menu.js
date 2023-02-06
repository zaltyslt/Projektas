import { NavLink } from "react-router-dom";
import "./Menu.css";

export function Menu() {
  return (
    <nav className="Navigation">
      <ul>
        <li>
          <NavLink to="/" className="Link">
            Tvarkaraščiai
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/teachers" className="Link">
            Dėstytojai
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/groups" className="Link">
            Grupės
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/modules" className="Link">
            Moduliai
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/subjects" className="Link">
            Dalykai
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/shifts" className="Link">
            Pamainos
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/rooms" className="Link">
            Klasės
          </NavLink>
        </li>
        &nbsp;|&nbsp;
        <li>
          <NavLink to="/programs" className="Link">
            Programos
          </NavLink>
        </li>
      </ul>
    </nav>
  );
}
