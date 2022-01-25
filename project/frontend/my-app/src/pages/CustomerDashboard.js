import React, { Component, Fragment, useEffect } from 'react'
// allow for links to other webpages
import { Link, Redirect } from 'react-router-dom'
import axios from 'axios'
import RequestPasswordChange from './customerDashboardComponents/RequestPasswordChange'
import OrderDrink from './customerDashboardComponents/OrderDrink'
import '../styles/CustomerDashboard.css'
import greenTeaImg from '../images/greenTea.jpg'
import blackTeaImg from '../images/blackTea.jpg'
import thaiTeaImg from '../images/thaiTea.jpg'

class CustomerDashboard extends Component {
  constructor() {
    super()

    this.state = {
      oldpassword: '',
      newpassword: ''
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.handleLogout = this.handleLogout.bind(this)
  }

  componentDidMount() {
    axios.get('http://localhost:8090/customers')
      .then(response => {
        console.log(response)
        response.data.forEach(customer => {
          if(customer.loggedIn) {
            this.setState({
              customer: customer,
              oldpassword: '',
              newpassword: '',
              email: customer.email,
            })
          }
        })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  // sets the state when inputs in sign up form are filled
  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    axios
      .post('http://localhost:8090/changePassword', this.state)
      .then((response) => {
        console.log(response)
        // const {temp} = this.state
        // this.setState({
        //   ...temp,
        //   status: response.data
        // })
        // console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  handleLogout() {
    axios
      .post('http://localhost:8090/logout', this.state)
      .then((response) => {
        console.log(response)
        const {temp} = this.state
        this.setState({
          ...temp,
          status: response.data
        })
        console.log(this.state)
      })
      .catch((error) => {
        console.log(error)
      })
  }

  render() {
    const { oldpassword, newpassword } = this.state

  if(this.state.status === 'Logout Successful') {
    return <Redirect to='/customerlogin' />
  }

    return (
      <div class='wrapper'>
        <div class='menuContent'>
          <div class='logoutButton'>
            <button onClick={this.handleLogout}>Log Out</button>
          </div>

          <RequestPasswordChange />

          <OrderDrink />
        </div>
      </div>
    )
  }
}

export default CustomerDashboard
