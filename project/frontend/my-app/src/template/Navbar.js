import React from 'react';
// import css
import './Navbar.css'

// allow for links to other webpages
import { Link } from 'react-router-dom';

// import Gong Cha Logo
import gongchaLogo from '../images/gongchaLogo.jpg';

function Navbar() {
    return (
        <div>
            { /*Gong Cha Logo above navbar*/ }
            <div class="gonchaLogo">
                <Link to='/home'><img class="gongchaLogoImg" src={gongchaLogo} alt="Gong Cha Logo" /></Link>
            </div>

            { /*navbar*/ }
            <div class="navbar">
                <ul class="navbarList">
                    <li class="navbarItem"><Link to='/home'>Home</Link></li>
                    <li class="navbarItem"><Link to='/menu'>Menu</Link></li>
                    <li class="navbarItem"><Link to='/signup'>Sign Up</Link></li>
                    <li class="navbarItem"><Link to='/customerlogin'>Customer Login</Link></li>
                    <li class="navbarItem"><Link to='/employeelogin'>Employee Login</Link></li>
                </ul>
            </div>
        </div>
    );
}

export default Navbar;