import React, { Component, Fragment, useEffect } from 'react'
// allow for links to other webpages
import { Link, Redirect } from 'react-router-dom'
import axios from 'axios'
import '../styles/Login.css'

class CustomerLogin extends Component {
  constructor() {
    super()

    this.state = {
      firstname: '',
      lastname: '',
      username: '',
      email: '',
      password: '',
      status: ''
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
    axios
      .post('http://localhost:8090/login', this.state)
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
    const { firstname, lastname, username, email, password } = this.state

    // redirect if logged in
    if(this.state.status === 'Login Successful')
    {
        return <Redirect to='/customerdashboard' />
    }

    // otherwise, have user log in
    else {
      return (
        <div class='wrapper'>
          <div class='menuContent'>
            <h2>Customer Login</h2>
            <form onSubmit={this.handleSubmit}>
              <label for='username'>E-mail:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='email'
                id='email'
                name='email'
                placeholder='john@doe.com'
                value={email}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <label for='password'>Password:</label>
              <br></br>
              <input
                onChange={this.handleChange}
                type='password'
                id='password'
                name='password'
                placeholder='password'
                value={password}
                size='70'
              ></input>
              <br></br>
              <br></br>

              <br></br>

              <button class='submit' type='submit'>
                Submit
              </button>
            </form>
          </div>
        </div>
      )
    }
  }
}

export default CustomerLogin
