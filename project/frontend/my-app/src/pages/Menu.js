import React, { Component, Fragment, useEffect } from 'react'
import axios from 'axios'
import '../styles/Menu.css'
import greenTeaImg from '../images/greenTea.jpg'
import blackTeaImg from '../images/blackTea.jpg'
import thaiTeaImg from '../images/thaiTea.jpg'

class Home extends Component {
  constructor() {
    super()
  }
  render() {
    return (
      <div class='wrapper'>
        <div class='menuContent'>
          <div class='menuItem'>
            <img class='menuItemImage' src={greenTeaImg} alt='Green Tea'></img>
            <div class='menuDetails'>
              <h3 class='menuItemName'>Green Tea (Tea/Milk Tea)</h3>

              <p class='drinksize'>Drink size:</p>
              <ul>
                  <li>Medium $4.00</li>  <li>Large $5.00</li>
              </ul>
            </div>
          </div>

          <div class='menuItem'>
            <img class='menuItemImage' src={blackTeaImg} alt='Black Tea'></img>
            <div class='menuDetails'>
              <h3 class='menuItemName'>Black Tea (Tea/Milk Tea)</h3>

              <p class='drinksize'>Drink size:</p>
              <ul>
                  <li>Medium $4.25</li>  <li>Large $5.00</li>
              </ul>
            </div>
          </div>

          <div class='menuItem'>
            <img class='menuItemImage' src={thaiTeaImg} alt='Thai Tea'></img>
            <div class='menuDetails'>
              <h3 class='menuItemName'>Thai Tea (Tea/Milk Tea)</h3>

              <p class='drinksize'>Drink size:</p>
              <ul>
                  <li>Medium $4.50</li>  <li>Large $5.00</li>
              </ul>
            </div>
          </div>
        </div>
      </div>
    )
  }
}

export default Home
