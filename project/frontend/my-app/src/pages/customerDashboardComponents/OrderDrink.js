import React, { Component, Fragment, useEffect } from 'react'
// allow for links to other webpages
import { Link, Redirect } from 'react-router-dom'
import axios from 'axios'
import '../../styles/CustomerDashboard.css'
import greenTeaImg from '../../images/greenTea.jpg'
import blackTeaImg from '../../images/blackTea.jpg'
import thaiTeaImg from '../../images/thaiTea.jpg'

class Home extends Component {
  constructor() {
    super()

    this.state = {
      drink: '',
      milk: '',
      drinkSize: '',
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
  }

  // sets the state when inputs in sign up form are filled
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()

    console.log(this.state)
    axios
      .post('http://localhost:8080/order/register', this.state)
      .then((response) => {
        console.log(response)
        const { temp } = this.state
        this.setState({
          ...temp,
          status: response.data,
        })
        console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  render() {
    const { drink, milk, drinkSize } = this.state

    if (
      this.state.status != null &&
      this.state.status.includes('Successful Order!')
    ) {
      let ordernumArray = this.state.status.split('-')
      let ordernum = ordernumArray[1]
      let amountArray = this.state.status.split('$')
      let amount = amountArray[1]
      return (
        <Redirect
          to={{
            pathname: '/payment',
            state: { ordernumber: ordernum, transactionamount: amount },
          }}
        />
      )
    }

    return (
      <div>
        <div class='sectionTitle'>
          <p>Menu</p>
        </div>
        {/* Thai Tea */}
        <div class='menuImages'>
          <img class='menuItemImage' src={greenTeaImg} alt='Thai Tea'></img>
          <img class='menuItemImage' src={blackTeaImg} alt='Thai Tea'></img>
          <img class='menuItemImage' src={thaiTeaImg} alt='Thai Tea'></img>
        </div>
        <div class='menuItem'>
          
          <div class='menuDetails'>
            <h3 class='menuItemName'>Order Here</h3>
            <form onSubmit={this.handleSubmit}>
              {/* Drink Type */}
              <p>Drink Type:</p> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='drink'
                name='drink'
                value='Green Tea'
              ></input>
              <label>Green Tea</label> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='drink'
                name='drink'
                value='Black Tea'
              ></input>
              <label>Black Tea</label> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='drink'
                name='drink'
                value='Thai Tea'
              ></input>
              <label>Thai Tea</label> {/* Drink Size */}
              <p>Drink size:</p> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='drinkSize'
                name='drinkSize'
                value='Medium'
              ></input>
                <label>Medium</label> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='drinkSize'
                name='drinkSize'
                value='Large'
              ></input>
                <label>Large</label>
              <br></br>
              {/* Milk Options */}
              <p>Milk/no milk:</p> 
              <input
                onChange={this.handleChange}
                type='radio'
                id='milk'
                name='milk'
                value='Milk'
              ></input>
                <label>Milk</label>
              <input
                onChange={this.handleChange}
                type='radio'
                id='milk'
                name='milk'
                value='No Milk'
              ></input>
                <label>No milk</label>
              <br></br>
              <br></br>
              <button class='submit' type='submit'>
                Submit
              </button>
            </form>
          </div>
        </div>
      </div>
    )
  }
}

export default Home
