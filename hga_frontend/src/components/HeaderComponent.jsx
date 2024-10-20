import { NavLink } from 'react-router-dom';

/** Code for the Header of the webpage. */
const HeaderComponent = () => {
  return (
    <div>
      <header>
        <nav className="navbar navbar-expand-sm navbar-dark bg-dark sticky-top px-4">
          <NavLink 
            className={({ isActive }) => `navbar-brand ${isActive ? 'active' : 'inactive'}`} 
            to="/"
          >
            Masters
          </NavLink>
          <div className="collapse navbar-collapse" id="navbarNav">
            <ul className="navbar-nav">
              <li className="nav-item">
                <NavLink 
                  className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} 
                  to="/dogs-and-kennels"
                >
                  Kennels and Dogs
                </NavLink>
              </li>
              <li className="nav-item">
                <NavLink 
                  className={({ isActive }) => `nav-link ${isActive ? 'active' : ''}`} 
                  to="/judges"
                >
                  Judges
                </NavLink>
              </li>
            </ul>
          </div>
        </nav>
      </header>
    </div>
  );
};

export default HeaderComponent;
