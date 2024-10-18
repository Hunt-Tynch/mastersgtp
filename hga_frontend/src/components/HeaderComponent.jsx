import { NavLink } from 'react-router-dom'

/** Code for the Header of the webpage. */
const HeaderComponent = () => {
  return (
    <div>
        <header>
            <nav className="navbar navbar-expand-sm navbar-dark bg-dark sticky-top px-4">
                <a className="navbar-brand" href="http://localhost:3000">Masters</a>
                <div className="collapse navbar-collapse" id="navbarNav">
                  <ul className="navbar-nav">
                    <li className="nav-item">
                      <NavLink className="nav-link" to="/dogs">Dogs</NavLink>
                    </li>
                  </ul>
                </div>
            </nav>
        </header>
    </div>
  )
}

export default HeaderComponent