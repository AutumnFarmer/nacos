#!/bin/bash
set -e

echo "🚀 Starting Kubernetes Environment Setup..."

# Check if Homebrew is installed
if ! command -v brew &> /dev/null; then
    echo "❌ Homebrew not found. Please install Homebrew first."
    exit 1
fi

# 1. Install kubectl
if ! command -v kubectl &> /dev/null; then
    echo "📦 Installing kubectl..."
    brew install kubectl
    
    # Setup completion
    echo "🛠️  Setting up kubectl auto-completion (zsh)..."
    if ! grep -q "kubectl completion zsh" ~/.zshrc; then
        echo 'source <(kubectl completion zsh)' >> ~/.zshrc
        echo 'alias k=kubectl' >> ~/.zshrc
        echo 'complete -F __start_kubectl k' >> ~/.zshrc
    fi
else
    echo "✅ kubectl is already installed."
fi

# 2. Install OrbStack
if ! ls /Applications/OrbStack.app &> /dev/null; then
    echo "📦 Installing OrbStack (Docker/K8s Desktop)..."
    brew install --cask orbstack
else
    echo "✅ OrbStack is already installed."
fi

echo "
🎉 Installation Complete!

👉 **Next Steps:**
1. Open **OrbStack** from your Applications folder.
2. Follow the setup wizard (it's very quick).
3. Ensure 'Kubernetes' is enabled in OrbStack settings if asked.
4. Run 'source ~/.zshrc' to enable kubectl auto-completion.
5. Run 'kubectl get nodes' to verify connection.
"
