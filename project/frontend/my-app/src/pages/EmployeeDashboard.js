import React, { Component, Fragment, useEffect } from 'react'
import { Redirect } from 'react-router-dom'
import axios from 'axios'
import '../styles/EmployeeDashboard.css'
import AllowPasswordChange from './employeeDashboardComponents/AllowPasswordChange'
import AllOrders from './employeeDashboardComponents/AllOrders'

const api = axios.create({
  baseURL: 'http://localhost:8090/customers'
})

class EmployeeDashboard extends Component {
  constructor() {
    super()

    this.state = {
      requests: [],
      firstname: '',
      lastname: '',
      email: ''
    }

    this.handleChange = this.handleChange.bind(this)
    this.handleSubmit = this.handleSubmit.bind(this)
    this.handleLogout = this.handleLogout.bind(this)
  }

  componentDidMount() {
    axios.get('http://localhost:8090/customers/requests')
      .then(response => {
        console.log(response)
          this.setState({
            requests: response.data
          })
        console.log(this.state)
      })
      .catch(error => {
        console.log(error)
      })
  }

  handleChange = (e) => {
    this.setState({ [e.target.name]: e.target.value })
  }

  // handles POST request when form is submitted
  handleSubmit = (e) => {
    e.preventDefault()
    console.log(this.state)
    axios
      .post('http://localhost:8090/customer/approve-request', this.state)
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

  handleLogout() {
    this.setState({
      status: 'Logout Successful'
    })
  }

  render() {
    const { firstname, lastname, email } = this.state

    if(this.state.status === 'Logout Successful') {
      return <Redirect to='/employeelogin' />
    }
    
    return (
      <div class='wrapper'>
        <div class='employeeDashboardContent'>
          <div class='logoutButton'>
            <button onClick={this.handleLogout}>Log Out</button>
          </div>
          
          <AllowPasswordChange />

          <AllOrders />
          
        </div>
      </div>
    )
  }
}

export default EmployeeDashboard
