SHELL := /bin/bash

# ----------------------------------------------------------------

MVN ?= $(if $(MVND_HOME),mvnd,mvn)

.PHONY: clean compile install test deploy package tree prepare perform check git_config

.DEFAULT_GOAL := help

# ----------------------------------------------------------------

dir:
	@echo "Current directory: $(CURDIR)"

# ----------------------------------------------------------------

clean: dir
	@echo "Cleaning the project..."
	$(MVN) clean

# ----------------------------------------------------------------

compile: clean
	@echo "Using $(MVN) to compile the project..."
	$(MVN) compile

# ----------------------------------------------------------------

install: clean
	@echo "Using $(MVN) to install the project..."
	$(MVN) install

# ----------------------------------------------------------------

test: clean
	@echo "Using $(MVN) to test the project..."
	$(MVN) test

deploy: clean
	@echo "Using $(MVN) to deploy the project..."
	$(MVN) -DskipTests=true source:jar deploy

package: clean
	@echo "Using $(MVN) to package the project..."
	$(MVN) -DskipTests=true package

tree:
	@echo "Using $(MVN) to show dependency tree..."
	$(MVN) dependency:tree -Dincludes=$(filter-out $@,$(MAKECMDGOALS))

# ----------------------------------------------------------------

prepare: clean
	@echo "Using $(MVN) to release:prepare the project..."
	$(MVN) release:prepare

perform:
	@echo "Using $(MVN) to release:perform the project..."
	$(MVN) release:perform

# ----------------------------------------------------------------

check:
	@echo "Using $(MVN) to checkstyle:check the project..."
	$(MVN) checkstyle:check

# ----------------------------------------------------------------

name?=Your Name
email?=you@example.com

git_config:
	@echo "Git user.name and user.email have been set globally."
	git config user.name "$(name)"
	git config user.email "$(email)"

# ----------------------------------------------------------------

help:
	@echo "Available targets:"
	@echo "  clean        - Clean the project"
	@echo "  compile      - Compile the project"
	@echo "  test         - Run tests"
	@echo "  deploy       - Deploy the project"
	@echo "  package      - Package the project"
	@echo "  tree         - Show dependency tree (e.g., make tree group:artifact | :artifact)"
	@echo "  docs         - Generate the html documents of web project"
	@echo "  prepare      - Release:prepare the project"
	@echo "  perform      - Release:perform the project"
	@echo "  check        - Checkstyle:check the project"
	@echo "  git_config   - Git:config git user.name and user.email of the project"
	@echo "  help         - Show this help message"

# ----------------------------------------------------------------

%:
	@echo "Unknown target: $@"
	@echo "Use 'make help' to see available targets."
