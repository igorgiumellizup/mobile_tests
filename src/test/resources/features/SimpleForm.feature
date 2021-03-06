#
# Copyright 2020 ZUP IT SERVICOS EM TECNOLOGIA E INOVACAO SA
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.
#

@simpleform @android @ios
Feature: SimpleForm Component Validation

    As a Beagle developer/user
    I'd like to make sure my SimpleForm component works as expected
    In order to guarantee that my application never fails


    Background:
        Given that I'm on the simple form screen

    Scenario: SimpleForm 01 - Checking if simpleForm children components appear on the screen
        Then checks that the textInput with the place holder Type in your email is on the screen
        Then checks that the textInput with the place holder Type in your name is on the screen
        Then checks that the button with the title Click to Submit is on the screen

    Scenario Outline: SimpleForm 02 - Verification of the operation of the onSubmit attribute
        When I click on textInput for email with Type in your email and insert my <email>
        When I click on textInput for name with Type in your name and insert my <name>
        When I click to Click to Submit
        Then verify if the email: <email> and the name: <name> appear correctly

        Examples:
            | email                | name     |
            | teste@simpleform.com | joao     |
