import React, { Component, Fragment, useEffect } from 'react'
import axios from 'axios'
import '../styles/Home.css'

class Home extends Component {
  constructor() {
    super()
  }
  render() {
    return (
      <div class='wrapper'>
        <div class='homeContent'>
          <h2>Welcome to Gong Cha!</h2>

          <p class='description'>View our drinks on the menu tab!</p>
          <p class='description'> If you want to order a drink, please log in as a customer.</p>
          <p class='description'>If you are an employee, please log in.</p>
        </div>
      </div>
    )
  }
}

export default Home
